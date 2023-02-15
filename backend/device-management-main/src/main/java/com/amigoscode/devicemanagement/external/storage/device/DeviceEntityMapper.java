package com.amigoscode.devicemanagement.external.storage.device;

import com.amigoscode.devicemanagement.domain.device.model.Device;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface DeviceEntityMapper {

    DeviceEntity toEntity(Device domain);

    Device toDomain(DeviceEntity entity);

}
