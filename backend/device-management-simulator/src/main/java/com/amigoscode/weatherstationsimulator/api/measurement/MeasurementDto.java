package com.amigoscode.weatherstationsimulator.api.measurement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
class MeasurementDto {
    Long id;
    String deviceId;
    Float temperature;
    Float pressure;
    Float humidity;
    WindDto wind;
    LocationDto location;
    ZonedDateTime timestamp;

}
