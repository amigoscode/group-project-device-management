package com.amigoscode.weatherstationsimulator.external.storage.measurement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class MeasurementDao {

    Integer id;

    String deviceId;
    Float temperature;
    Float pressure;
    Float humidity;
    WindDao wind;
    LocationDao location;
    ZonedDateTime timestamp;


    public MeasurementDao(final String deviceId,
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
        MeasurementDao that = (MeasurementDao) o;
        return id.equals(that.id);
    }
    @Override
    public int hashCode() {
        return 0;
    }
}
