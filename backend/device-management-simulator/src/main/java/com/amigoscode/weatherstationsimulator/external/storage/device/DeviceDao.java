package com.amigoscode.weatherstationsimulator.external.storage.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class DeviceDao {
    String deviceId;
    String ownerId;
    String name;
}
