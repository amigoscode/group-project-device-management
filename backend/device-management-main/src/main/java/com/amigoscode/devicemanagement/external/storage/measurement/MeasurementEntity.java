package com.amigoscode.devicemanagement.external.storage.measurement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@Document("Measurements")
@TypeAlias("MeasurementEntity")
@CompoundIndexes({
        @CompoundIndex(name = "deviceId_timestamp", def = "{'deviceId' : 1, 'timestamp': 1}")
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class MeasurementEntity {

    @Id
    private String id;

    String deviceId;
    Float temperature;
    Float pressure;
    Float humidity;
    WindDao wind;
    LocationDao location;
    ZonedDateTime timestamp;


    public MeasurementEntity(final String deviceId,
                      final Float temperature,
                      final Float pressure,
                      final Float humidity,
                      final WindDao wind,
                      final LocationDao location,
                      final ZonedDateTime timestamp) {
        this.deviceId = deviceId;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.wind = wind;
        this.location = location;
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementEntity that = (MeasurementEntity) o;
        return id.equals(that.id);
    }
    @Override
    public int hashCode() {
        return 0;
    }
}
