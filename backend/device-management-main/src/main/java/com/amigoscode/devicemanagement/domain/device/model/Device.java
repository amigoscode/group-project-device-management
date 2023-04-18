package com.amigoscode.devicemanagement.domain.device.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.Value;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@ToString
public class
Device implements Serializable {

    String id;
    String name;
    String ownerId;
    ZonedDateTime createdAt;
    ZonedDateTime deletedAt;
    ZonedDateTime updatedAt;
    String updatedBy;

    public boolean isUserTheOwnerOfThisDevice(String userId) {
        return ownerId.equals(userId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Device device = (Device) o;

        if (!Objects.equals(id, device.id)) return false;
        if (!Objects.equals(name, device.name)) return false;
        if (!Objects.equals(ownerId, device.ownerId)) return false;
        if (!isItTheSameDate(createdAt, device.createdAt)) return false;
        if (!isItTheSameDate(updatedAt, device.updatedAt)) return false;
        if (!isItTheSameDate(deletedAt, device.deletedAt)) return false;
        return Objects.equals(updatedBy, device.updatedBy);
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
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (ownerId != null ? ownerId.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (deletedAt != null ? deletedAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (updatedBy != null ? updatedBy.hashCode() : 0);
        return result;
    }
}
