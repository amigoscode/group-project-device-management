package com.amigoscode.weatherstationsimulator.external.mqtt;

import com.amigoscode.weatherstationsimulator.domain.temperature.TemperaturePublishing;
import com.amigoscode.weatherstationsimulator.domain.temperature.model.Temperature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;


@Component
class TemperatureMqttAdapter implements TemperaturePublishing {

    MqttGateway mqtGateway;
    TemperatureMqttDtoMapper temperatureMqttDtoMapper;

    TemperatureMqttAdapter(final MqttGateway mqtGateway, final TemperatureMqttDtoMapper temperatureMqttDtoMapper) {
        this.mqtGateway = mqtGateway;
        this.temperatureMqttDtoMapper = temperatureMqttDtoMapper;
    }

    @Value("${device.id}")
    String deviceId;

    private static final Logger log = LoggerFactory.getLogger(MqttBeans.class);

    @Override
    public void publish(final Temperature temperature) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
//        String jsonInString = null;
//        try {
//            jsonInString = mapper.writeValueAsString(temperatureMqttDtoMapper.toDto(temperature));
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        String topic = deviceId + "/temperature";
//        mqtGateway.senToMqtt(jsonInString, topic);

        //fake
        String measurementTopic = "dm/measurements";
        String id = temperature.getId().toString();
        LoactionMqttDto fakeLoaction = new LoactionMqttDto(19.457216f, 51.759445f, 278.0f);
        WindMqttDto fakeWind = new WindMqttDto(2.57f, 125.1f);
        String deviceId = "3";
        Float fakeTemperature = temperature.getValue();
        Float fakePressure = 1013.0f;
        Float fakeHumidity = 123.45f;
        ZonedDateTime timestamp = temperature.getTimestamp();

        MeasurementMqttDto fakeMeasurement = new MeasurementMqttDto(
                deviceId,
                fakeTemperature,
                fakePressure,
                fakeHumidity,
                fakeWind,
                fakeLoaction,
                timestamp
        );

        String measurementJsonInString = null;
        try {
            measurementJsonInString = mapper.writeValueAsString(fakeMeasurement);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        mqtGateway.senToMqtt(measurementJsonInString, measurementTopic);

    }
}
