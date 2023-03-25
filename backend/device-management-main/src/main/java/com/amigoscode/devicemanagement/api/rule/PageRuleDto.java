package com.amigoscode.devicemanagement.api.rule;

import lombok.Value;

import java.util.List;

@Value
class PageRuleDto {

    List<RuleDto> rules;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}
