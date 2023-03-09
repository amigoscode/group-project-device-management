package com.amigoscode.devicemanagement.api.devicesetting;

import lombok.Value;

import java.time.ZonedDateTime;

@Value
class DeviceSettingDto {

    String id;
    String deviceId;
    Integer measurementPeriod;
    Boolean measurementEnabled;
    ZonedDateTime createdAt;
    ZonedDateTime deletedAt;
    ZonedDateTime updatedAt;
    String updatedBy;
}
