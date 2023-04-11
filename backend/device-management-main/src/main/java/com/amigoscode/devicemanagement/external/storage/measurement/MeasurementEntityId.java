package com.amigoscode.devicemanagement.external.storage.measurement;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amigoscode.devicemanagement.external.storage.utils.ZonedDateTimeConverter;


import java.time.ZonedDateTime;

public class MeasurementEntityId {

    private String deviceId;
    private ZonedDateTime timestamp;

    public MeasurementEntityId() {
    }

    public MeasurementEntityId(final String deviceId, final ZonedDateTime timestamp) {
        this.deviceId = deviceId;
        this.timestamp = timestamp;
    }

    @DynamoDBHashKey(attributeName = "DeviceId")
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(final String deviceId) {
        this.deviceId = deviceId;
    }

    @DynamoDBRangeKey(attributeName = "Timestamp")
    @DynamoDBTypeConverted(converter = ZonedDateTimeConverter.class)
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "MeasurementEntityId{" +
                "deviceId='" + deviceId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
