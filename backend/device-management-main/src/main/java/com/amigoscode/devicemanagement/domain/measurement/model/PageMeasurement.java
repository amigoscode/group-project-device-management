package com.amigoscode.devicemanagement.domain.measurement.model;

import com.amigoscode.devicemanagement.domain.device.model.Device;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class PageMeasurement implements Serializable {

    List<Measurement> measurements;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}
