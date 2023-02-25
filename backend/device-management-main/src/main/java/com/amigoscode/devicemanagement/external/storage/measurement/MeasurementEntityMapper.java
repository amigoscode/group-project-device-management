package com.amigoscode.devicemanagement.external.storage.measurement;

import com.amigoscode.devicemanagement.domain.measurement.model.Measurement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface MeasurementEntityMapper {

    MeasurementEntity toEntity(Measurement domain);

    Measurement toDomain(MeasurementEntity entity);

}
