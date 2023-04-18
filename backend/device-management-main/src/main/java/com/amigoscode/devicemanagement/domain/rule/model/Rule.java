package com.amigoscode.devicemanagement.domain.rule.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@ToString
public class Rule implements Serializable {


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
