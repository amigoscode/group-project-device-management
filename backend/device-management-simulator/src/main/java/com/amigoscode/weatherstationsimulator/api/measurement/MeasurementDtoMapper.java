package com.amigoscode.weatherstationsimulator.api.measurement;

import com.amigoscode.weatherstationsimulator.domain.measurement.Measurement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface MeasurementDtoMapper {

    MeasurementDto toDto(Measurement domain);

    Measurement toDomain(MeasurementDto dto);
}
