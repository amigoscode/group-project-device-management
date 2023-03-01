package com.amigoscode.weatherstationsimulator.external.storage.measurement;

import com.amigoscode.weatherstationsimulator.domain.measurement.Measurement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public
interface MeasurementDaoMapper {

    MeasurementDao toEntity(Measurement domain);

    Measurement toDomain(MeasurementDao entity);

}
