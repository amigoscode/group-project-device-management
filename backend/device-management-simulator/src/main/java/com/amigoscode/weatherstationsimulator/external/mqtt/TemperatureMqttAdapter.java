package com.amigoscode.weatherstationsimulator.external.mqtt;

import com.amigoscode.weatherstationsimulator.domain.temperature.TemperaturePublishing;
import com.amigoscode.weatherstationsimulator.domain.temperature.model.Temperature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


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
        String jsonInString = null;
        try {
            jsonInString = mapper.writeValueAsString(temperatureMqttDtoMapper.toDto(temperature));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String topic = deviceId + "/temperature";
        mqtGateway.senToMqtt(jsonInString, topic);

    }
}
