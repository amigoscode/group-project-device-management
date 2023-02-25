package com.amigoscode.devicemanagement.external.mqtt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
class LocationMqttDto {
    Float longitude;
    Float latitude;
    Float elevation;
}
