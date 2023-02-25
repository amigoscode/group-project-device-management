package com.amigoscode.weatherstationsimulator.external.mqtt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
class WindMqttDto {
    Float speed;
    Float direction;
}
