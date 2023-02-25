package com.amigoscode.devicemanagement.domain.measurement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@ToString
public class Measurement {
    String id;
    String deviceId;
    Float temperature;
    Float pressure;
    Float humidity;
    Wind wind;
    Location location;
    ZonedDateTime timestamp;

    public boolean wasMadeByDeviceWithId(final String deviceId) {
        return this.deviceId.equals(deviceId);
    }
}
