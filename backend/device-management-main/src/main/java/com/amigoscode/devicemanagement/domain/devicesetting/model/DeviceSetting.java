package com.amigoscode.devicemanagement.domain.devicesetting.model;

import lombok.Value;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Value
public class DeviceSetting  implements Serializable {

    String id;
    String deviceId;
    Integer measurementPeriod;
    boolean isMeasurementEnabled;
    ZonedDateTime createdAt;
    ZonedDateTime deletedAt;
    ZonedDateTime updatedAt;
    String updatedBy;

    public boolean isDeviceTheOwnerOfThisSetting(String id) {
        return deviceId.equals(id);
    }

}
