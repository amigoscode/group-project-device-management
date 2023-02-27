package com.amigoscode.weatherstationsimulator.api.measurement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
class WindDto {
    Float speed;
    Float direction;
}
