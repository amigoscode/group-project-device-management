package com.amigoscode.weatherstationsimulator.domain.measurement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Location {
    Float longitude;
    Float latitude;
    Float elevation;
}
