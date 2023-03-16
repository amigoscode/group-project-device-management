package com.amigoscode.devicemanagement.external.storage.utils;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class ZonedDateTimeConverter implements DynamoDBTypeConverter<String, ZonedDateTime> {

    @Override
    public String convert(ZonedDateTime date) {
        return date.toString();
    }

    @Override
    public ZonedDateTime unconvert(final String stringValue) {
        return ZonedDateTime.parse(stringValue);
    }

}
