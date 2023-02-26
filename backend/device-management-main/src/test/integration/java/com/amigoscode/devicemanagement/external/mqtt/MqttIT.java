package com.amigoscode.devicemanagement.external.mqtt;


import com.amigoscode.devicemanagement.BaseIT;
import com.amigoscode.devicemanagement.TestDeviceFactory;
import com.amigoscode.devicemanagement.TestMeasurementFactory;
import com.amigoscode.devicemanagement.domain.device.DeviceService;
import com.amigoscode.devicemanagement.domain.device.model.Device;
import com.amigoscode.devicemanagement.domain.measurement.MeasurementService;
import com.amigoscode.devicemanagement.domain.measurement.model.Measurement;
import com.amigoscode.devicemanagement.domain.measurement.model.PageMeasurement;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MqttIT extends BaseIT {


    private String mqttClientId = MqttAsyncClient.generateClientId();

    @Autowired
    private Environment environment;

    @Autowired
    MeasurementService measurementService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    MeasurementMqttMapper measurementMqttMapper;

    @Autowired
    MqttPahoClientFactory mqttPahoClientFactory;

    protected IMqttClient client;

    @BeforeEach
    public void setUp() throws Exception {
        MqttConnectOptions options = new MqttConnectOptions();

        options.setServerURIs(new String[] { environment.getProperty("mqtt.server.uri") });
        options.setUserName("admin");
        String pass = "qwerty";
        options.setPassword(pass.toCharArray());
        options.setCleanSession(true);

        client = new MqttClient(environment.getProperty("mqtt.server.uri"), mqttClientId);
        client.connect(options);
    }

    @AfterEach
    public void tearDown() throws Exception {
        client.disconnect();
        client.close();
    }

    @Test
    public void mqtt_client_should_be_able_to_publish_message() throws Exception {
        String mqttTopic = "abc";
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload("MQTT!".getBytes());
        client.publish(mqttTopic, mqttMessage);
    }

    @Test
    public void the_measurement_published_in_the_right_topic_by_the_registered_device_should_be_saved_in_the_repository() throws Exception {
        //given
        Device device = TestDeviceFactory.createRandom();
        deviceService.save(device);
        MeasurementMqttDto measurement = measurementMqttMapper.toMqtt(TestMeasurementFactory.createRandom());
        measurement.setDeviceId(device.getId());
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String measurementJsonInString = mapper.writeValueAsString(measurement);

        //when
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(measurementJsonInString.getBytes());
        String measurementTopic = "dm/measurements";
        client.publish(measurementTopic, mqttMessage);

        //then
        TimeUnit.SECONDS.sleep(1);
        int page = 0;
        int size = 3;
        Pageable pageable = PageRequest.of(page, size);
        String deviceId = device.getId();
        PageMeasurement pageWithFoundMeasurements = measurementService.findAllByDeviceId(pageable, deviceId);
        assertEquals(1, pageWithFoundMeasurements.getTotalElements());
        compareMeasurements(measurementMqttMapper.toDomain(measurement), pageWithFoundMeasurements.getMeasurements().get(0));
    }

    @Test
    public void the_measurement_published_in_the_inappropriate_topic_by_the_registered_device_should_not_be_saved_in_the_repository() throws Exception {
        //given
        Device device = TestDeviceFactory.createRandom();
        deviceService.save(device);
        MeasurementMqttDto measurement = measurementMqttMapper.toMqtt(TestMeasurementFactory.createRandom());
        measurement.setDeviceId(device.getId());
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String measurementJsonInString = mapper.writeValueAsString(measurement);

        //when
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(measurementJsonInString.getBytes());
        String measurementTopic = "dm/wrongTopic";
        client.publish(measurementTopic, mqttMessage);

        //then
        TimeUnit.SECONDS.sleep(1);
        int page = 0;
        int size = 3;
        Pageable pageable = PageRequest.of(page, size);
        String deviceId = device.getId();
        PageMeasurement pageWithFoundMeasurements = measurementService.findAllByDeviceId(pageable, deviceId);
        assertEquals(0, pageWithFoundMeasurements.getTotalElements());
    }

    @Test
    public void the_measurement_published_in_the_right_topic_by_the_unregistered_device_should_not_be_saved_in_the_repository() throws Exception {
        //given
        Device device = TestDeviceFactory.createRandom();
        MeasurementMqttDto measurement = measurementMqttMapper.toMqtt(TestMeasurementFactory.createRandom());
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String measurementJsonInString = mapper.writeValueAsString(measurement);

        //when
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(measurementJsonInString.getBytes());
        String measurementTopic = "dm/measurements";
        client.publish(measurementTopic, mqttMessage);

        //then
        TimeUnit.SECONDS.sleep(1);
        int page = 0;
        int size = 3;
        Pageable pageable = PageRequest.of(page, size);
        String deviceId = device.getId();
        PageMeasurement pageWithFoundMeasurements = measurementService.findAllByDeviceId(pageable, deviceId);
        assertEquals(0, pageWithFoundMeasurements.getTotalElements());
    }

    private void compareMeasurements(Measurement model, Measurement tested) {
        Assertions.assertNotNull(tested);
        assertEquals(model.getDeviceId(), tested.getDeviceId());
        assertEquals(model.getTemperature(), tested.getTemperature());
        assertEquals(model.getPressure(), tested.getPressure());
        assertEquals(model.getHumidity(), tested.getHumidity());
        assertEquals(model.getWind(), tested.getWind());
        assertEquals(model.getLocation(), tested.getLocation());
        assertEquals(model.getTimestamp().toLocalDateTime(), tested.getTimestamp().toLocalDateTime());
    }

}

