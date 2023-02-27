package com.amigoscode.weatherstationsimulator.external.storage.device;

import com.amigoscode.weatherstationsimulator.domain.device.Device;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceDaoMapper {

    DeviceDao toDao(Device domain);

    Device toDomain(DeviceDao dao);
}
