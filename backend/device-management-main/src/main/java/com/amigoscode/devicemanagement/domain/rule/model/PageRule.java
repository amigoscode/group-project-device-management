package com.amigoscode.devicemanagement.domain.rule.model;


import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class PageRule implements Serializable {

    List<Rule> rules;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}
