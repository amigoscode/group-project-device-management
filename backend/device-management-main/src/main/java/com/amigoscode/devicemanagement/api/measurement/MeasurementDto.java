package com.amigoscode.devicemanagement.api.measurement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@ToString
class MeasurementDto {
    String id;
    String deviceId;
    Float temperature;
    Float pressure;
    Float humidity;
    WindDto wind;
    LocationDto location;
    ZonedDateTime timestamp;

}
