package com.amigoscode.devicemanagement.api.device;

import lombok.Value;

import java.time.ZonedDateTime;

@Value
class DeviceDto {

    String id;
    String name;
    String ownerId;
    boolean isEnabled;
    boolean isOnline;
    ZonedDateTime createdAt;
    ZonedDateTime updatedAt;
    ZonedDateTime deletedAt;
    String updatedBy;
}
