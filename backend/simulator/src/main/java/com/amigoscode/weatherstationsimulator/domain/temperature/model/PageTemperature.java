package com.amigoscode.weatherstationsimulator.domain.temperature.model;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class PageTemperature implements Serializable {
    List<Temperature> temperatures;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}
