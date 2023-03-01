package com.amigoscode.devicemanagement.external.mqtt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@ToString
public class MeasurementMqttDto implements Serializable {
    String deviceId;
    Float temperature;
    Float pressure;
    Float humidity;
    WindMqttDto wind;
    LocationMqttDto location;
    ZonedDateTime timestamp;
}
