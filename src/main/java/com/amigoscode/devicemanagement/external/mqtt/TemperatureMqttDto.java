package com.amigoscode.devicemanagement.external.mqtt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@ToString
public class TemperatureMqttDto implements Serializable {
    Long id;
    Float value;
    ZonedDateTime timestamp;
}
