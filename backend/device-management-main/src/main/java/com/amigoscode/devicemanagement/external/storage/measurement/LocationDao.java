package com.amigoscode.devicemanagement.external.storage.measurement;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@DynamoDBDocument
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LocationDao {
    @DynamoDBAttribute
    Float longitude;
    @DynamoDBAttribute
    Float latitude;
    @DynamoDBAttribute
    Float elevation;
}
