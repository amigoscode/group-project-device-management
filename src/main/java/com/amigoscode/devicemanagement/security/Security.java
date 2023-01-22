package com.amigoscode.devicemanagement.security;

import com.amigoscode.devicemanagement.domain.user.UserService;
import com.amigoscode.devicemanagement.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class Security {

    private final UserService userService;

    public User getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(((UserPrincipal) authentication.getPrincipal()).getUsername());
    }
}
