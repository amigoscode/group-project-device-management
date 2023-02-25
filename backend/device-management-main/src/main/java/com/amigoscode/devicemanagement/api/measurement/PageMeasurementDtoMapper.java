package com.amigoscode.devicemanagement.api.measurement;

import com.amigoscode.devicemanagement.domain.measurement.model.Measurement;
import com.amigoscode.devicemanagement.domain.measurement.model.PageMeasurement;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
interface PageMeasurementDtoMapper {

    @Mapping(target = "measurements", qualifiedByName = "toMeasurementDtoList")
    PageMeasurementDto toPageDto(PageMeasurement domain);

    @Named("toMeasurementDtoList")
    @IterableMapping(qualifiedByName = "measurementToMeasurementDto")
    List<MeasurementDto> toListDto(List<Measurement> measurements);

    @Named("measurementToMeasurementDto")
    MeasurementDto  toDto(Measurement domain);
}
