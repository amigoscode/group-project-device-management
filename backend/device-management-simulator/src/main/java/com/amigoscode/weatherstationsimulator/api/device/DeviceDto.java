package com.amigoscode.weatherstationsimulator.api.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
class DeviceDto {
    String id;
    String ownerId;
    String name;
}
