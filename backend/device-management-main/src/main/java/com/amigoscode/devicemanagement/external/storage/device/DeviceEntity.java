package com.amigoscode.devicemanagement.external.storage.device;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Document("Devices")
@TypeAlias("DeviceEntity")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class DeviceEntity {

    @NotNull(message = "")
    @Id
    private String id;

    @NotNull(message = "")
    @Indexed(unique = true)
    private String name;

    @NotNull(message = "")
    private String ownerId;
    @NotNull(message = "")
    private boolean isEnabled;
    private boolean isOnline;

//    @DBRef
//    DeviceSettingEntity deviceSetting;

    @NotNull(message = "")
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime deletedAt;
    private String updatedBy;


    public DeviceEntity(String name, String ownerId, boolean isEnabled) {
        this.name = name;
        this.ownerId = ownerId;
        this.isEnabled = isEnabled;
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
