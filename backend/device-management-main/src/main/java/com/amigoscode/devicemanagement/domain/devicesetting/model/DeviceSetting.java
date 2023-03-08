package com.amigoscode.devicemanagement.domain.devicesetting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@ToString
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
