package com.amigoscode.weatherstationsimulator.external.mqtt;


import com.amigoscode.weatherstationsimulator.BaseIT;
import com.amigoscode.weatherstationsimulator.TestMeasurementFactory;
import com.amigoscode.weatherstationsimulator.domain.device.DeviceService;
import com.amigoscode.weatherstationsimulator.domain.measurement.Measurement;
import com.amigoscode.weatherstationsimulator.domain.measurement.MeasurementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MqttIT extends BaseIT {


    private String mqttClientId = MqttAsyncClient.generateClientId();

    @Autowired
    MeasurementService measurementService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    MeasurementMqttMapper measurementMqttMapper;

    @Test
    public void mqtt_client_should_be_able_to_publish_message() throws Exception {
        String mqttTopic = "abc";
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload("MQTT!".getBytes());
        client.publish(mqttTopic, mqttMessage);
    }

    @Test
    void take_and_publish_measurement_method_should_publish_measurement_if_it_is_not_empty() throws MqttException, InterruptedException {
        //given
        Measurement fakeMeasurement = TestMeasurementFactory.createRandom();
        measurementService.save(fakeMeasurement);
        String measurementTopic = "dm/measurements";
        client.getTopic(measurementTopic);

        final SimpleMqttEvents mqttCallback = new SimpleMqttEvents();
        client.setCallback(mqttCallback);

        int subQoS = 0;
        client.subscribe(measurementTopic, subQoS);


        //when
        final Optional<Measurement> measurement = measurementService.takeAndPublishMeasurement();


        //then
        TimeUnit.SECONDS.sleep(1);
//        assertEquals(1, mqttCallback.measurements.size());
//        compareMeasurements(measurement.get(), mqttCallback.measurements.get(0));
        assertTrue(mqttCallback.measurements.stream().anyMatch(measurement1 -> areMeasurementsEqual(measurement.get(), measurement1)));
    }

    @Test
    void take_and_publish_measurement_method_should_not_publish_measurement_if_it_is_empty() throws MqttException, InterruptedException {
        //given
        String measurementTopic = "dm/measurements";
        client.getTopic(measurementTopic);

        final SimpleMqttEvents mqttCallback = new SimpleMqttEvents();
        client.setCallback(mqttCallback);

        int subQoS = 0;
        client.subscribe(measurementTopic, subQoS);


        //when
        final Optional<Measurement> measurement = measurementService.takeAndPublishMeasurement();


        //then
        TimeUnit.SECONDS.sleep(1);
        assertEquals(0, mqttCallback.measurements.size());
    }

    class SimpleMqttEvents implements MqttCallback {

        private final List<Measurement> measurements = new ArrayList<>();

        @Override
        public void connectionLost(final Throwable throwable) {

        }

        @Override
        public void messageArrived(final String topic, final MqttMessage mqttMessage) throws Exception {

            if (topic.equals("dm/measurements")) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.findAndRegisterModules();

                try {
                    MeasurementMqttDto measurementMqttDto = mapper.readValue(mqttMessage.toString(), MeasurementMqttDto.class);
                    measurements.add(measurementMqttMapper.toDomain(measurementMqttDto));
                }
                catch (JsonProcessingException e) {
                    throw new JsonCouldNotBeCreatedException();
                }
            }
        }

        @Override
        public void deliveryComplete(final IMqttDeliveryToken iMqttDeliveryToken) {

        }
    }

    private boolean areMeasurementsEqual(Measurement model, Measurement tested) {
        if (tested == null) return false;
        if (!model.getDeviceId().equals(tested.getDeviceId())) return false;
        if (!model.getTemperature().equals(tested.getTemperature())) return false;
        if (!model.getPressure().equals(tested.getPressure())) return false;
        if (!model.getHumidity().equals(tested.getHumidity())) return false;
        if (!model.getWind().equals(tested.getWind())) return false;
        if (!model.getLocation().equals(tested.getLocation())) return false;
        if (!model.getTimestamp().toLocalDateTime().equals(tested.getTimestamp().toLocalDateTime())) return false;

        return true;
    }

}

