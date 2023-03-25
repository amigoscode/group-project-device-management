package com.amigoscode.devicemanagement.domain.rule.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@ToString
public class Rule implements Serializable {


     String id;
     String name;
     Boolean isActive;
     String topicPattern;
     String payloadPattern;
     Set<RuleCallBackMethod> method;
     String webhookUrl;
     ZonedDateTime createdAt;
     ZonedDateTime updatedAt;
     ZonedDateTime deletedAt;
     String updatedBy;
}
