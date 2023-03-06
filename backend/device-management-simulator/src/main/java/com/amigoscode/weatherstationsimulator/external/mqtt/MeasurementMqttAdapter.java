package com.amigoscode.weatherstationsimulator.external.mqtt;

import com.amigoscode.weatherstationsimulator.domain.measurement.Measurement;
import com.amigoscode.weatherstationsimulator.domain.measurement.MeasurementPublishing;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public
class MeasurementMqttAdapter implements MeasurementPublishing {

    MqttGateway mqtGateway;
    MeasurementMqttMapper measurementMqttMapper;

    private static final Logger log = LoggerFactory.getLogger(MeasurementMqttAdapter.class);

    public MeasurementMqttAdapter(final MqttGateway mqtGateway, final MeasurementMqttMapper measurementMqttMapper) {
        this.mqtGateway = mqtGateway;
        this.measurementMqttMapper = measurementMqttMapper;
    }

    @Override
    public void publish(final Measurement measurement) {
        String measurementTopic = "dm/measurements";
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        MeasurementMqttDto measurementMqttDto = measurementMqttMapper.toMqtt(measurement);

        String measurementJsonInString = null;
        try {
            measurementJsonInString = mapper.writeValueAsString(measurementMqttDto);
        } catch (JsonProcessingException e) {
            throw new JsonCouldNotBeCreatedException();
        }

        mqtGateway.senToMqtt(measurementJsonInString, measurementTopic);
    }

}
