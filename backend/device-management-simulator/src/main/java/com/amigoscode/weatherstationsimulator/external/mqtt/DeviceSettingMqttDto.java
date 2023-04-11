package com.amigoscode.weatherstationsimulator.external.mqtt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class DeviceSettingMqttDto {
    String deviceId;
    Integer measurementPeriod;
    Boolean isMeasurementEnabled;
}
