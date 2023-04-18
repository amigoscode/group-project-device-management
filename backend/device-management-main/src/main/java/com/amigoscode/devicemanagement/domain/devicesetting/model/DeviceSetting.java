package com.amigoscode.devicemanagement.domain.devicesetting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@ToString
public class DeviceSetting  implements Serializable {

    String id;
    String deviceId;
    Integer measurementPeriod;
    Boolean isMeasurementEnabled;
    ZonedDateTime createdAt;
    ZonedDateTime deletedAt;
    ZonedDateTime updatedAt;
    String updatedBy;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final DeviceSetting that = (DeviceSetting) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(deviceId, that.deviceId)) return false;
        if (!Objects.equals(measurementPeriod, that.measurementPeriod))
            return false;
        if (!Objects.equals(isMeasurementEnabled, that.isMeasurementEnabled))
            return false;
        if (!isItTheSameDate(createdAt, that.createdAt)) return false;
        if (!isItTheSameDate(updatedAt, that.updatedAt)) return false;
        if (!isItTheSameDate(deletedAt, that.deletedAt)) return false;
        return Objects.equals(updatedBy, that.updatedBy);
    }

    private boolean isItTheSameDate(final ZonedDateTime date1, final ZonedDateTime date2) {
        if(date1 == null && date2 == null) return true;
        if(date1 != null && date2 == null) return false;
        if(date1 == null && date2 != null) return false;
        return Objects.equals(date1.toInstant(), date2.toInstant());
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        result = 31 * result + (measurementPeriod != null ? measurementPeriod.hashCode() : 0);
        result = 31 * result + (isMeasurementEnabled != null ? isMeasurementEnabled.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (deletedAt != null ? deletedAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (updatedBy != null ? updatedBy.hashCode() : 0);
        return result;
    }
}
