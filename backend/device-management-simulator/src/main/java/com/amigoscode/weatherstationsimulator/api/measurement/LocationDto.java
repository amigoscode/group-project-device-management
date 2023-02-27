package com.amigoscode.weatherstationsimulator.api.measurement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
class LocationDto {
    Float longitude;
    Float latitude;
    Float elevation;
}
