package com.amigoscode.devicemanagement.domain.rule.model;

public enum RuleCallBackMethod {

    GET("GET"),
    POST("POST");

    private final String value;

    RuleCallBackMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
