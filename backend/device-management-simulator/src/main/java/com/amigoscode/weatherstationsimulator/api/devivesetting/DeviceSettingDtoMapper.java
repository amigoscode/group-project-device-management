package com.amigoscode.weatherstationsimulator.api.devivesetting;

import com.amigoscode.weatherstationsimulator.domain.devicesetting.DeviceSetting;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface DeviceSettingDtoMapper {

    DeviceSettingDto toDto(DeviceSetting domain);

    DeviceSetting toDomain(DeviceSettingDto dto);
}
