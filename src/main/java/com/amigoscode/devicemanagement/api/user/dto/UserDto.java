package com.amigoscode.devicemanagement.api.user.dto;

import lombok.Value;

import java.util.Set;

@Value
public class UserDto {

    String id;
    String email;
    String name;
    String password;
    Set<String> roles;
}