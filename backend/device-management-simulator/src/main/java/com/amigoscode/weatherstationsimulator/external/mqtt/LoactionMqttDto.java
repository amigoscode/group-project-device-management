package com.amigoscode.weatherstationsimulator.external.mqtt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
class LoactionMqttDto {
    Float longitude;
    Float latitude;
    Float elevation;
}
