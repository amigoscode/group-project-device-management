package com.amigoscode.devicemanagement.external.storage.devicesetting;

import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface DeviceSettingEntityMapper {

    DeviceSettingEntity toEntity(DeviceSetting domain);

    DeviceSetting toDomain(DeviceSettingEntity entity);
}
