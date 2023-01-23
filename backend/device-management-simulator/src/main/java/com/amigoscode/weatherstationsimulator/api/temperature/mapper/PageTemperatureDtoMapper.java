package com.amigoscode.weatherstationsimulator.api.temperature.mapper;

import com.amigoscode.weatherstationsimulator.api.temperature.dto.PageTemperatureDto;
import com.amigoscode.weatherstationsimulator.api.temperature.dto.TemperatureDto;
import com.amigoscode.weatherstationsimulator.domain.temperature.model.PageTemperature;
import com.amigoscode.weatherstationsimulator.domain.temperature.model.Temperature;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageTemperatureDtoMapper {
    @Mapping(target = "temperatures", qualifiedByName = "toTemperatureDtoList")
    PageTemperatureDto toPageDto(PageTemperature domain);

    @Named("toTemperatureDtoList")
    @IterableMapping(qualifiedByName = "temperatureToTemperatureDto")
    List<TemperatureDto> toListDto(List<Temperature> temperatures);

    @Named("temperatureToTemperatureDto")
    TemperatureDto toDto(Temperature domain);
}
