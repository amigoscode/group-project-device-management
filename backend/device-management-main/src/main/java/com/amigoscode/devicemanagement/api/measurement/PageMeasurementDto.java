package com.amigoscode.devicemanagement.api.measurement;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
class PageMeasurementDto implements Serializable {

    List<MeasurementDto> measurements;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}
