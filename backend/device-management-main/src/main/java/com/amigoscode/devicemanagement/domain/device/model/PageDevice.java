package com.amigoscode.devicemanagement.domain.device.model;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class PageDevice implements Serializable {

    List<Device> devices;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}
