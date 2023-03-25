package com.amigoscode.devicemanagement.api.rule;

import lombok.Value;

import java.time.ZonedDateTime;
import java.util.Set;

@Value
class RuleDto {

    String id;
    String name;
    Boolean isActive;
    String topicPattern;
    String payloadPattern;
    Set<String> method;
    String webhookUrl;
    ZonedDateTime createdAt;
    ZonedDateTime updatedAt;
    ZonedDateTime deletedAt;
    String updatedBy;
}
