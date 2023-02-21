package com.amigoscode.devicemanagement.api.device;

import com.amigoscode.devicemanagement.domain.device.model.Device;
import com.amigoscode.devicemanagement.domain.device.model.PageDevice;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
interface PageDeviceDtoMapper {

    @Mapping(target = "devices", qualifiedByName = "toDeviceDtoList")
    PageDeviceDto toPageDto(PageDevice domain);

    @Named("toDeviceDtoList")
    @IterableMapping(qualifiedByName = "deviceToDeviceDto")
    List<DeviceDto> toListDto(List<Device> devices);

    @Named("deviceToDeviceDto")
    DeviceDto  toDto(Device domain);
}
