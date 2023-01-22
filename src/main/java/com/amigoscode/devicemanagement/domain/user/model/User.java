package com.amigoscode.devicemanagement.domain.user.model;

import lombok.Value;

import java.io.Serializable;
import java.util.Set;

@Value
public class User implements Serializable {

    String id;
    String email;
    String name;
    String password;
    Set<UserRole> roles;

    public User withPassword(String newPassword) {
        return new User(id, email, name, newPassword, roles);
    }
}