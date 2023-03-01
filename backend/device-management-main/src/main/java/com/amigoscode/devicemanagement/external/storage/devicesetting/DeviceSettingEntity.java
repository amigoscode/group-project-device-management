package com.amigoscode.devicemanagement.external.storage.devicesetting;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@Document("DeviceSettings")
@TypeAlias("DeviceSettingEntity")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeviceSettingEntity {


    @Id
    private String  id;


    @Indexed(unique = true)
    private String deviceId;


    private Integer measurementPeriod;

    private boolean isMeasurementEnabled;


    private ZonedDateTime createdAt;
    private ZonedDateTime deletedAt;
    private ZonedDateTime updatedAt;
    private String updatedBy;


    public DeviceSettingEntity(String deviceId, Integer measurementPeriod, boolean isMeasurementEnabled) {
        this.deviceId = deviceId;
        this.measurementPeriod = measurementPeriod;
        this.isMeasurementEnabled = isMeasurementEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceSettingEntity that = (DeviceSettingEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
