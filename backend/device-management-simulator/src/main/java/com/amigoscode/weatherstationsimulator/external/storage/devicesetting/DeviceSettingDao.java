package com.amigoscode.weatherstationsimulator.external.storage.devicesetting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeviceSettingDao {
    String deviceId;
    Integer measurementPeriod;
    Boolean isMeasurementEnabled;
}
