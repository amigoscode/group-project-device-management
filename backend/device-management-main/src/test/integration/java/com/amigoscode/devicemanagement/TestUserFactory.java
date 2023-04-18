package com.amigoscode.devicemanagement;

import com.amigoscode.devicemanagement.domain.user.model.User;
import com.amigoscode.devicemanagement.domain.user.model.UserRole;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

public class TestUserFactory {

    private static int userSequence = 0;

    public static User createDeviceOwner() {
        userSequence++;
        return new User(
                "TEST" + userSequence,
                "newUser" + userSequence + "@example.com",
                "User Name " + userSequence,
                "password",
                Set.of(UserRole.DEVICE_OWNER),
                ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
                "Updated By " + userSequence
        );
    }

    public static User createAdmin() {
        userSequence++;
        return new User(
                "TEST" + userSequence,
                "newUser" + userSequence + "@example.com",
                "User Name " + userSequence,
                "password",
                Set.of(UserRole.ADMIN),
                ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
                "Updated By " + userSequence
        );
    }
}
