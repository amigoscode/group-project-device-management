package com.amigoscode.devicemanagement.external.mqtt;

import com.amigoscode.devicemanagement.domain.measurement.model.Measurement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface MeasurementMqttMapper {

    MeasurementMqttDto toMqtt(Measurement domain);

    Measurement toDomain(MeasurementMqttDto mqttDto);

}
