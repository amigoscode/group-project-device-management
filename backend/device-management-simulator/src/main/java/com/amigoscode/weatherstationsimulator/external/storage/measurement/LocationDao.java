package com.amigoscode.weatherstationsimulator.external.storage.measurement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
class LocationDao {
    Float longitude;
    Float latitude;
    Float elevation;
}
