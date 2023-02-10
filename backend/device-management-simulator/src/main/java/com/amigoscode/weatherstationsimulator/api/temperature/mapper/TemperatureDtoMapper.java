package com.amigoscode.weatherstationsimulator.api.temperature.mapper;

import com.amigoscode.weatherstationsimulator.api.temperature.dto.TemperatureDto;
import com.amigoscode.weatherstationsimulator.domain.temperature.model.Temperature;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TemperatureDtoMapper {
    TemperatureDto toDto(Temperature domain);

    Temperature toDomain(TemperatureDto dto);
}
