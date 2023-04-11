package com.amigoscode.devicemanagement.domain.measurement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@ToString
public class Measurement {
    String deviceId;
    Float temperature;
    Float pressure;
    Float humidity;
    Wind wind;
    Location location;
    ZonedDateTime timestamp;
}
