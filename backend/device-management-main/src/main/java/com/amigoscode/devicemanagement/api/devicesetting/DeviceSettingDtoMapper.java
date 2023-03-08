package com.amigoscode.devicemanagement.api.devicesetting;

import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface DeviceSettingDtoMapper {
    DeviceSettingDto toDto(DeviceSetting domain);

    DeviceSetting toDomain(DeviceSettingDto dto);
}
