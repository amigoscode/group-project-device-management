package com.amigoscode.weatherstationsimulator.domain.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Device {
    String deviceId;
    String ownerId;
    String name;
}
