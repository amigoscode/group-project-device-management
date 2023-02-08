package com.amigoscode.devicemanagement.api.user;

import lombok.Value;

import java.util.List;

@Value
class PageUserDto {

    List<UserDto> users;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}