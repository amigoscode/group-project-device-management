package com.amigoscode.weatherstationsimulator.domain.measurement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@ToString
public class Measurement {
    Integer id;
    String deviceId;
    Float temperature;
    Float pressure;
    Float humidity;
    Wind wind;
    Location location;
    ZonedDateTime timestamp;
}
