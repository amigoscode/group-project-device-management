package com.amigoscode.weatherstationsimulator.external.storage.devicesetting;

import com.amigoscode.weatherstationsimulator.domain.devicesetting.DeviceSetting;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceSettingDaoMapper {

    DeviceSettingDao toDao(DeviceSetting domain);

    DeviceSetting toDomain(DeviceSettingDao dao);
}
