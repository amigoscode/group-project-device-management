package com.amigoscode.weatherstationsimulator.api.device;

import com.amigoscode.weatherstationsimulator.domain.device.Device;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface DeviceDtoMapper {

    DeviceDto toDto(Device domain);

    Device toDomain(DeviceDto dto);
}
