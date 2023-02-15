package com.amigoscode.devicemanagement.external.storage.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@Document("Devices")
@TypeAlias("DeviceEntity")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeviceEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String ownerId;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
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
        return id.equals(that.id) && name.equals(that.name) && ownerId.equals(that.ownerId) && createdAt.equals(that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(deletedAt, that.deletedAt) && Objects.equals(updatedBy, that.updatedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ownerId, createdAt, updatedAt, deletedAt, updatedBy);
    }

    @Override
    public String toString() {
        return "DeviceEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
