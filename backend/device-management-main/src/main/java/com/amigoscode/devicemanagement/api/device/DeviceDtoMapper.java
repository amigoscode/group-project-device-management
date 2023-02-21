package com.amigoscode.devicemanagement.api.device;

import com.amigoscode.devicemanagement.domain.device.model.Device;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface DeviceDtoMapper {

    DeviceDto toDto(Device domain);

    Device toDomain(DeviceDto dto);
}
