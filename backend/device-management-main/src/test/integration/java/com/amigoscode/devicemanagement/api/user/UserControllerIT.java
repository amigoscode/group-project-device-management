package com.amigoscode.devicemanagement.api.user;


import com.amigoscode.devicemanagement.domain.user.UserService;
import com.amigoscode.devicemanagement.domain.user.model.User;
import com.amigoscode.devicemanagement.domain.user.model.UserRole;
import com.amigoscode.devicemanagement.api.response.ErrorResponse;
import com.amigoscode.devicemanagement.BaseIT;
import com.amigoscode.devicemanagement.TestUserFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserControllerIT extends BaseIT {

    @Autowired
    UserService service;

    @Test
    void admin_should_get_information_about_any_user() {
        //given
        User user = TestUserFactory.createDeviceOwner();
        service.save(user, "creatorId");
        String token = getTokenForAdmin();

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/users/" + user.getId(),
                token,
                null,
                UserDto.class);

        //then
        UserDto body = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(body);
        assertEquals(user.getId(), body.getId());
        assertEquals(user.getEmail(), body.getEmail());
        assertEquals(user.getName(), body.getName());
        assertEquals("######", body.getPassword());
        assertEquals(user.getRoles().toString(), body.getRoles().toString());
    }

    @Test
    void admin_should_get_response_code_404_when_user_not_exits_in_db() {
        //given
        String token = getTokenForAdmin();

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/users/fakeId",
                token,
                null,
                ErrorResponse.class);

        //then
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void device_owner_should_not_get_information_about_other_device_owner() {
        //given
        User user1 = TestUserFactory.createDeviceOwner();
        User user2 = TestUserFactory.createDeviceOwner();
        service.save(user1, "creatorId");
        service.save(user2, "creatorId");
        String accessToken = getAccessTokenForUser(user1.getEmail(), user1.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/users/" + user2.getId(),
                accessToken,
                null,
                ErrorResponse.class);

        //then
        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    void admin_should_get_response_code_conflict_when_user_is_in_db() {
        //given
        User user = TestUserFactory.createDeviceOwner();
        service.save(user, "creatorId");
        String adminToken = getTokenForAdmin();

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/users",
                adminToken,
                user,
                ErrorResponse.class);

        //then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }


    @Test
    void admin_should_be_able_to_save_new_user() {
        //given
        User user = TestUserFactory.createDeviceOwner();
        String adminAccessToken = getTokenForAdmin();

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/users",
                adminAccessToken,
                user,
                UserDto.class);

        //then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        //and
        UserDto body = response.getBody();
        assertNotNull(body);
        assertEquals(body.getEmail(), user.getEmail());
        assertEquals(body.getName(), user.getName());
        assertEquals(body.getPassword(), "######");
        assertEquals(body.getRoles().toString(), user.getRoles().toString());
    }

    @Test
    void device_owner_should_get_information_about_himself() {
        //given
        User user = TestUserFactory.createDeviceOwner();
        service.save(user, "creatorId");
        String accessToken = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/users/me",
                accessToken,
                null,
                UserDto.class);

        //then
        UserDto body = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        //and
        assertNotNull(body);
        assertEquals(body.getId(), user.getId());
        assertEquals(body.getEmail(), user.getEmail());
        assertEquals(body.getName(), user.getName());
        assertEquals(body.getPassword(), "######");
        assertEquals(body.getRoles().toString(), user.getRoles().toString());
    }

    @Test
    void admin_should_be_able_to_update_user() {
        //given
        User user = TestUserFactory.createDeviceOwner();
        userService.save(user, "creatorId");
        User toUpdate = new User(
                user.getId(),
                "email@email.com",
                "newPerson",
                "newpassword",
                Set.of(UserRole.DEVICE_OWNER),
                user.getCreatedAt(),
                user.getDeletedAt(),
                user.getUpdatedAt(),
                user.getUpdatedBy()
        );
        String adminAccessToken = getTokenForAdmin();

        //when
        var response = callHttpMethod(HttpMethod.PUT,
                "/api/v1/users",
                adminAccessToken,
                toUpdate,
                UserDto.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void admin_should_be_get_response_code_200_when_update_user_not_exits() {
        //given
        String token = getTokenForAdmin();
        User fakeUser = TestUserFactory.createDeviceOwner();

        //when
        var response = callHttpMethod(HttpMethod.PUT,
                "/api/v1/users",
                token,
                fakeUser,
                ErrorResponse.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void device_owner_should_be_not_able_to_update_user() {
        //given
        User user = TestUserFactory.createDeviceOwner();
        userService.save(user, "creatorId");
        User userToUpdate = new User(
                user.getId(),
                "otherUser@email.com",
                "Person",
                "password",
                Set.of(UserRole.DEVICE_OWNER),
                user.getCreatedAt(),
                user.getDeletedAt(),
                user.getUpdatedAt(),
                user.getUpdatedBy()
        );
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.PUT,
                "/api/v1/users",
                token,
                userToUpdate,
                ErrorResponse.class);

        //then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void admin_should_be_able_to_delete_user() {
        //given
        User user = TestUserFactory.createDeviceOwner();
        String adminAccessToken = getTokenForAdmin();
        userService.save(user, "creatorId");

        //when
        var response = callHttpMethod(
                HttpMethod.DELETE,
                "/api/v1/users/" + user.getId(),
                adminAccessToken,
                null,
                UserDto.class);

        //then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void admin_should_get_response_code_204_when_user_not_exits() {
        //given
        User user = TestUserFactory.createDeviceOwner();
        String token = getTokenForAdmin();

        //when
        var response = callHttpMethod(
                HttpMethod.DELETE,
                "/api/v1/users/" + user.getId(),
                token,
                null,
                ErrorResponse.class);

        //then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void device_owner_should_not_be_able_to_delete_user() {
        //given
        User firstUser = TestUserFactory.createDeviceOwner();
        User secondUser = TestUserFactory.createDeviceOwner();
        userService.save(firstUser, "creatorId");
        userService.save(secondUser, "creatorId");
        String token = getAccessTokenForUser(firstUser.getEmail(), firstUser.getPassword());

        //when
        var response = callHttpMethod(
                HttpMethod.DELETE,
                "/api/v1/users/" + secondUser.getId(),
                token,
                null,
                ErrorResponse.class);

        //then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void admin_should_get_pageable_list_of_users() {
        //give
        User user = TestUserFactory.createDeviceOwner();
        String adminAccessToken = getTokenForAdmin();
        userService.save(user, "creatorId");

        //when
        var response = callHttpMethod(
                HttpMethod.GET,
                "/api/v1/users",
                adminAccessToken,
                null,
                PageUserDto.class
        );

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PageUserDto body = response.getBody();
        //and
        assertNotNull(body);
        assertEquals(body.getUsers().size(), 2);
        assertEquals(body.getTotalElements(), 2L);
        assertEquals(body.getTotalPages(), 1);
        assertEquals(body.getCurrentPage(), 1);
        //and users passwords should be hashed
        assertTrue(
                body.getUsers().stream()
                        .allMatch(userDto -> userDto.getPassword().equals("######"))
        );
    }
}
