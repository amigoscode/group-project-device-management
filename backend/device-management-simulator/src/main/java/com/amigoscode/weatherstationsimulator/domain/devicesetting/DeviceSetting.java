package com.amigoscode.weatherstationsimulator.domain.devicesetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@ToString
public class DeviceSetting implements Serializable {
    String deviceId;
    Integer measurementPeriod;
    Boolean isMeasurementEnabled;
}
