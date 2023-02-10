package com.amigoscode.weatherstationsimulator.external.storage.temperature;

import com.amigoscode.weatherstationsimulator.domain.temperature.model.Temperature;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TemperatureEntityMapper {
    TemperatureEntity toEntity(Temperature domain);

    Temperature toDomain(TemperatureEntity entity);
}
