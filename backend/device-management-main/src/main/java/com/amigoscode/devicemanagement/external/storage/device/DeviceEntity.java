package com.amigoscode.devicemanagement.external.storage.device;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@Document("Devices")
@TypeAlias("DeviceEntity")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class DeviceEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String ownerId;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime deletedAt;
    private String updatedBy;


    public DeviceEntity(String name, String ownerId) {
        this.name = name;
        this.ownerId = ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceEntity that = (DeviceEntity) o;
        return id.equals(that.id);
    }
    @Override
    public int hashCode() {
        return 0;
    }
}
