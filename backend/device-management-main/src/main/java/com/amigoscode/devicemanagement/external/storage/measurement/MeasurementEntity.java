package com.amigoscode.devicemanagement.external.storage.measurement;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amigoscode.devicemanagement.external.storage.utils.ZonedDateTimeConverter;
import org.springframework.data.annotation.Id;

import java.time.ZonedDateTime;
import java.util.Objects;

@DynamoDBTable(tableName = "Measurements")
public class MeasurementEntity {

    @Id
    private MeasurementEntityId measurementEntityId;

    @DynamoDBAttribute
    private Float temperature;
    @DynamoDBAttribute
    private Float pressure;
    @DynamoDBAttribute
    private Float humidity;
    @DynamoDBAttribute
    private WindDao wind;
    @DynamoDBAttribute
    private LocationDao location;

    public MeasurementEntity() {
    }

    public MeasurementEntity(
            final MeasurementEntityId measurementEntityId,
            final Float temperature,
            final Float pressure,
            final Float humidity,
            final WindDao wind,
            final LocationDao location) {
        this.measurementEntityId = measurementEntityId;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.wind = wind;
        this.location = location;
    }

    @DynamoDBHashKey(attributeName = "DeviceId")
    public String getDeviceId() {
        return measurementEntityId != null ? measurementEntityId.getDeviceId() : null;
    }

    public void setDeviceId(final String deviceId) {
        if (measurementEntityId == null) {
            measurementEntityId = new MeasurementEntityId();
        }
        measurementEntityId.setDeviceId(deviceId);
    }

    @DynamoDBRangeKey(attributeName = "Timestamp")
    @DynamoDBTypeConverted(converter = ZonedDateTimeConverter.class)
    public ZonedDateTime getTimestamp() {
        return measurementEntityId != null ? measurementEntityId.getTimestamp() : null;
    }

    public void setTimestamp(final ZonedDateTime timestamp) {
        if (measurementEntityId == null) {
            measurementEntityId = new MeasurementEntityId();
        }
        measurementEntityId.setTimestamp(timestamp);
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(final Float temperature) {
        this.temperature = temperature;
    }

    public Float getPressure() {
        return pressure;
    }

    public void setPressure(final Float pressure) {
        this.pressure = pressure;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(final Float humidity) {
        this.humidity = humidity;
    }

    public WindDao getWind() {
        return wind;
    }

    public void setWind(final WindDao wind) {
        this.wind = wind;
    }

    public LocationDao getLocation() {
        return location;
    }

    public void setLocation(final LocationDao location) {
        this.location = location;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final MeasurementEntity that = (MeasurementEntity) o;

        return Objects.equals(measurementEntityId, that.measurementEntityId);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "MeasurementEntity{" +
                "measurementEntityId=" + measurementEntityId +
                ", temperature=" + temperature +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", wind=" + wind +
                ", location=" + location +
                '}';
    }

}
