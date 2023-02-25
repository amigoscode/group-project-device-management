package com.amigoscode.devicemanagement.domain.measurement.model;

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
