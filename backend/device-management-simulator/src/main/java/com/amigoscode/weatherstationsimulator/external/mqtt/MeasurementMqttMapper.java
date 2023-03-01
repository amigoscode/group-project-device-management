package com.amigoscode.weatherstationsimulator.external.mqtt;

import com.amigoscode.weatherstationsimulator.domain.measurement.Measurement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public
interface MeasurementMqttMapper {

    MeasurementMqttDto toMqtt(Measurement domain);

    Measurement toDomain(MeasurementMqttDto mqttDto);

}
