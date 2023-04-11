package com.amigoscode.devicemanagement.external.mqtt;

import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface DeviceSettingMqttMapper {

    DeviceSettingMqttDto toMqtt(DeviceSetting domain);

    DeviceSetting toDomain(DeviceSettingMqttDto mqttDto);

}
