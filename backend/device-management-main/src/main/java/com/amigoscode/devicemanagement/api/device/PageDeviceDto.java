package com.amigoscode.devicemanagement.api.device;

import lombok.Value;

import java.util.List;

@Value
class PageDeviceDto {

    List<DeviceDto> devices;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}
