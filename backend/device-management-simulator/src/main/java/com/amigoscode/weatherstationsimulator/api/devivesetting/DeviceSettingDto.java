package com.amigoscode.weatherstationsimulator.api.devivesetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
class DeviceSettingDto {
    String deviceId;
    Integer measurementPeriod;
    Boolean isMeasurementEnabled;
}
