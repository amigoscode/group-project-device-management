package com.amigoscode.weatherstationsimulator.external.mqtt;

import com.amigoscode.weatherstationsimulator.domain.temperature.model.Temperature;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TemperatureMqttDtoMapper {
    TemperatureMqttDto toDto(Temperature domain);

    Temperature toDomain(TemperatureMqttDto dto);
}
