package com.amigoscode.devicemanagement;

import com.amigoscode.devicemanagement.domain.user.UserService;
import com.amigoscode.devicemanagement.domain.user.model.User;
import com.amigoscode.devicemanagement.domain.user.model.UserRole;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Log
public class DefaultUsers implements CommandLineRunner {

    private final UserService userService;

    public DefaultUsers(UserService userService) {
        this.userService = userService;
    }

    private final User adminUser = new User(
        null,
        "jan@example.com",
        "Jan Kowalski",
        "MyPassword",
        Set.of(UserRole.ADMIN)
    );

    private final User studentUser = new User(
        null,
        "stefan@example.com",
        "Stefan Burczymucha",
        "password",
        Set.of(UserRole.STUDENT)
    );

    @Override
    public void run(String... args) {
        try {
            addUser(adminUser);
            addUser(studentUser);
        } catch (Exception ex) {
            log.warning("Users already exist");
        }
    }

    private void addUser(User user) {
        userService.save(user);
    }
}
