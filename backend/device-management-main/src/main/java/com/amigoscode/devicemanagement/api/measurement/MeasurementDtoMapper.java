package com.amigoscode.devicemanagement.api.measurement;

import com.amigoscode.devicemanagement.domain.measurement.model.Measurement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface MeasurementDtoMapper {

    MeasurementDto toDto(Measurement domain);

    Measurement toDomain(MeasurementDto dto);
}
