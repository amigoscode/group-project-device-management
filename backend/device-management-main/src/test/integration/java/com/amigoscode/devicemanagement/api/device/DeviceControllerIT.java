package com.amigoscode.devicemanagement.api.device;


import com.amigoscode.devicemanagement.BaseIT;
import com.amigoscode.devicemanagement.TestDeviceFactory;
import com.amigoscode.devicemanagement.TestUserFactory;
import com.amigoscode.devicemanagement.api.response.ErrorResponse;
import com.amigoscode.devicemanagement.domain.device.DeviceService;
import com.amigoscode.devicemanagement.domain.device.model.Device;
import com.amigoscode.devicemanagement.domain.user.UserService;
import com.amigoscode.devicemanagement.domain.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DeviceControllerIT extends BaseIT {

    @Autowired
    UserService userService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    DeviceDtoMapper deviceDtoMapper;

    @Autowired
    PageDeviceDtoMapper pageDeviceDtoMapper;

    @Test
    void admin_should_be_able_to_get_information_about_device() {
        //given
        String adminAccessToken = getTokenForAdmin();
        Device device = TestDeviceFactory.createRandom();
        deviceService.save(device);

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/devices/" + device.getId(),
                adminAccessToken,
                null,
                DeviceDto.class);

        //then
        DeviceDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        compareDevices(device, deviceDtoMapper.toDomain(body));
    }

    @Test
    void user_should_be_able_to_get_information_about_device_he_owns() {
        User user = TestUserFactory.createDeviceOwner();
        Device device = TestDeviceFactory.createRandom();
        device.setOwnerId(user.getId());
        userService.save(user);
        deviceService.save(device);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/devices/" + device.getId(),
                token,
                null,
                DeviceDto.class);

        //then
        DeviceDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        compareDevices(device, deviceDtoMapper.toDomain(body));
    }

    @Test
    void user_should_not_be_able_to_get_information_about_device_he_does_not_own() {
        User user = TestUserFactory.createDeviceOwner();
        Device device = TestDeviceFactory.createRandom();
        device.setOwnerId(user.getId() + "qwerty");
        userService.save(user);
        deviceService.save(device);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/devices/" + device.getId(),
                token,
                null,
                ErrorResponse.class);

        //then
        Assertions.assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    @Test
    void admin_should_be_able_to_get_information_about_all_devices() {
        //given
        String adminAccessToken = getTokenForAdmin();
        Device device1 = TestDeviceFactory.createRandom();
        Device device2 = TestDeviceFactory.createRandom();
        Device device3 = TestDeviceFactory.createRandom();
        deviceService.save(device1);
        deviceService.save(device2);
        deviceService.save(device3);

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/devices",
                adminAccessToken,
                null,
                PageDeviceDto.class);

        //then
        PageDeviceDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        assertEquals(3, body.getTotalElements());
        compareDevices(device1, deviceDtoMapper.toDomain(body.getDevices().get(0)));
        compareDevices(device2, deviceDtoMapper.toDomain(body.getDevices().get(1)));
        compareDevices(device3, deviceDtoMapper.toDomain(body.getDevices().get(2)));
    }

    @Test
    void user_should_be_able_to_get_information_about_all_devices_he_owns() {

    }

    @Test
    void admin_should_be_able_to_save_new_device() {
        //given
        String adminAccessToken = getTokenForAdmin();
        Device device = TestDeviceFactory.createRandom();

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/devices",
                adminAccessToken,
                deviceDtoMapper.toDto(device),
                DeviceDto.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        DeviceDto body = response.getBody();
        //and
        compareDevices(device, deviceDtoMapper.toDomain(body));
    }

    @Test
    void user_should_not_be_able_to_save_new_device() {
        //given
        User user = TestUserFactory.createDeviceOwner();
        Device device = TestDeviceFactory.createRandom();
        userService.save(user);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/devices",
                token,
                deviceDtoMapper.toDto(device),
                ErrorResponse.class);

        //then
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void should_return_conflict_about_duplicated_device() {
        //given
        String adminAccessToken = getTokenForAdmin();
        Device device = TestDeviceFactory.createRandom();
        deviceService.save(device);

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/devices",
                adminAccessToken,
                deviceDtoMapper.toDto(device),
                ErrorResponse.class);

        //then
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void admin_should_be_able_to_delete_device() {
        //given
        String adminAccessToken = getTokenForAdmin();
        Device device = TestDeviceFactory.createRandom();
        deviceService.save(device);

        //when
        var response = callHttpMethod(HttpMethod.DELETE,
                "/api/v1/devices/" + device.getId(),
                adminAccessToken,
                deviceDtoMapper.toDto(device),
                Void.class);

        //then
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void user_should_not_be_able_to_delete_device() {
        //given
        User user = TestUserFactory.createDeviceOwner();
        Device device = TestDeviceFactory.createRandom();
        deviceService.save(device);
        userService.save(user);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.DELETE,
                "/api/v1/devices/" + device.getId(),
                token,
                deviceDtoMapper.toDto(device),
                ErrorResponse.class);

        //then
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    private void compareDevices(Device model, Device tested) {
        assertNotNull(tested);
        assertEquals(model.getId(), tested.getId());
        assertEquals(model.getName(), tested.getName());
        assertEquals(model.getOwnerId(), tested.getOwnerId());
        assertEquals(model.getCreatedAt(), tested.getCreatedAt());
        assertEquals(model.getDeletedAt(), tested.getDeletedAt());
        assertEquals(model.getUpdatedAt(), tested.getUpdatedAt());
        assertEquals(model.getUpdatedBy(), tested.getUpdatedBy());
    }

}
