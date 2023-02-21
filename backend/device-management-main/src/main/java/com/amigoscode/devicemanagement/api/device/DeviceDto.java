package com.amigoscode.devicemanagement.api.device;

import lombok.Value;

import java.time.ZonedDateTime;

@Value
class DeviceDto {

    String id;
    String name;
    String ownerId;
    ZonedDateTime createdAt;
    ZonedDateTime updatedAt;
    ZonedDateTime deletedAt;
    String updatedBy;
}
