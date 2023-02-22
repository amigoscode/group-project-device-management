package com.amigoscode.devicemanagement.domain.device.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.Value;

import java.io.Serializable;
import java.time.ZonedDateTime;

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
}
