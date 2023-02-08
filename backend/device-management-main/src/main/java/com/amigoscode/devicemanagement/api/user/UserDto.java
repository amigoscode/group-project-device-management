package com.amigoscode.devicemanagement.api.user;

import lombok.Value;

import java.util.Set;

@Value
class UserDto {

    String id;
    String email;
    String name;
    String password;
    Set<String> roles;
}