package com.amigoscode.devicemanagement.api.rule;

import lombok.Value;

import java.time.ZonedDateTime;

@Value
class RuleDto {

    String id;
    String name;
    Boolean isActive;
    String topicPattern;
    String payloadPattern;
    String method;
    String webhookUrl;
    ZonedDateTime createdAt;
    ZonedDateTime updatedAt;
    ZonedDateTime deletedAt;
    String updatedBy;
}
