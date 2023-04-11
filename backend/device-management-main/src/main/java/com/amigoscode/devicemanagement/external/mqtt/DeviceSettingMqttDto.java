package com.amigoscode.devicemanagement.external.mqtt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@ToString
public class DeviceSettingMqttDto {
    String deviceId;
    Integer measurementPeriod;
    Boolean isMeasurementEnabled;
}
