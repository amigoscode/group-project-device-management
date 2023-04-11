package com.amigoscode.weatherstationsimulator.external.mqtt;

import com.amigoscode.weatherstationsimulator.domain.devicesetting.DeviceSetting;
import com.amigoscode.weatherstationsimulator.domain.measurement.Measurement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public
interface DeviceSettingMqttMapper {

    DeviceSettingMqttDto toMqtt(DeviceSetting domain);

    DeviceSetting toDomain(DeviceSettingMqttDto mqttDto);

}
