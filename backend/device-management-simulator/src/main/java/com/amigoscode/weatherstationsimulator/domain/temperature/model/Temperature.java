package com.amigoscode.weatherstationsimulator.domain.temperature.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@ToString
public class Temperature {
    Long id;
    Float value;
    ZonedDateTime timestamp;
}
