package com.amigoscode.devicemanagement.api.device;


import com.amigoscode.devicemanagement.BaseIT;
import com.amigoscode.devicemanagement.TestDeviceFactory;
import com.amigoscode.devicemanagement.TestUserFactory;
import com.amigoscode.devicemanagement.TestDesignSettingFactory;
import com.amigoscode.devicemanagement.api.response.ErrorResponse;
import com.amigoscode.devicemanagement.domain.device.DeviceService;
import com.amigoscode.devicemanagement.domain.device.model.Device;
import com.amigoscode.devicemanagement.domain.user.UserService;
import com.amigoscode.devicemanagement.domain.user.model.User;
import com.amigoscode.devicemanagement.domain.devicesetting.DeviceSettingService;
import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DeviceSettingControllerIT extends BaseIT {

    @Autowired
    UserService userService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    DeviceService deviceSettingService;

    @Autowired
    DeviceDtoMapper DeviceSettingDtoMapper;



    @Test
    void admin_should_be_able_to_get_information_about_device_setting() {
        //given
        String adminAccessToken = getTokenForAdmin();
        Device device = TestDeviceFactory.createRandom();
        DeviceSetting deviceSetting = TestDeviceSettingFactory.createRandom();
        deviceSettingService.save(deviceSetting);
        deviceService.save(device);

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/devices/" + device.getId() + "/settings/" + deviceSetting.getId(),
                adminAccessToken,
                null,
                DeviceSettingDto.class);

        //then
        DeviceSettingDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        compareDeviceSettings(deviceSetting, DeviceSettingDtoMapper.toDomain(body));
    }

    @Test
    void device_owner_should_be_able_to_get_device_setting_information_about_device_he_owns() {
        //given
        User user = TestUserFactory.createDeviceOwner();
        Device device = TestDeviceFactory.createRandom();
        DeviceSetting deviceSetting = TestDeviceSettingFactory.createRandom();
        device.setOwnerId(user.getId());
        deviceService.save(device);
        deviceSettingService.save(deviceSetting);

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/devices/" + device.getId() + "/settings/" + deviceSetting.getId(),
                token,
                null,
                DeviceSettingDto.class);

        //then
        DeviceSettingDto body = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        compareDeviceSettings(deviceSetting, deviceSettingDtoMapper.toDomain(body));
    }

    @Test
    void device_owner_should_not_be_able_to_get_device_setting_information_about_he_does_not_own() {
        User user = TestUserFactory.createDeviceOwner();
        Device device = TestDeviceFactory.createRandom();
        DeviceSetting deviceSetting = TestDeviceSettingFactory.createRandom();
        device.setOwnerId(user.getId() + "qwerty");
        userService.save(user);
        deviceService.save(device);
        deviceSettingService.save(deviceSetting);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/devices/" + device.getId() + "/settings/" + deviceSetting.getId(),
                token,
                null,
                ErrorResponse.class);

        //then
        Assertions.assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    @Test
    void admin_should_be_able_to_save_new_device_setting() {
        //given
        String adminAccessToken = getTokenForAdmin();
        Device device = TestDeviceFactory.createRandom();
        deviceService.save(device);

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/devices/" + device.getId() + "/settings",
                adminAccessToken,
                deviceSettingDtoMapper.toDto(deviceSetting),
                DeviceSettingDto.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        DeviceSettingDto body = response.getBody();
        //and
        compareDevices(deviceSetting, deviceSettingDtoMapper.toDomain(body));
    }

    @Test
    void device_owner_should_be_able_to_save_new_device_setting() {
        //given
        User user = TestUserFactory.createDeviceOwner();
        Device device = TestDeviceFactory.createRandom();
        userService.save(user);
        deviceService.save(device);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/devices/" + device.getId() + "/settings",
                token,
                deviceSettingDtoMapper.toDto(deviceSetting),
                DeviceSettingDto.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        DeviceSettingDto body = response.getBody();
        //and
        compareDevices(deviceSetting, deviceSettingDtoMapper.toDomain(body));
    }

    @Test
    void should_return_conflict_about_duplicated_device_setting() {
        //given
        String adminAccessToken = getTokenForAdmin();
        Device device = TestDeviceFactory.createRandom();
        DeviceSetting deviceSetting = TestDeviceSettingFactory.createRandom();
        deviceService.save(device);
        deviceSettingService.save(deviceSetting);

        //when
        var response = callHttpMethod(HttpMethod.POST,
                "/api/v1/devices/" + device.getId() + "/settings",
                adminAccessToken,
                deviceSettingDtoMapper.toDto(deviceSetting),
                ErrorResponse.class);

        //then
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void admin_should_be_able_to_update_device_setting() {
        //given
        String adminAccessToken = getTokenForAdmin();
        Device device = TestDeviceFactory.createRandom();
        DeviceSetting deviceSetting = TestDeviceSettingFactory.createRandom();
        deviceService.save(device);
        deviceSettingService.save(deviceSetting);

        DeviceSetting updatedDeviceSetting = new DeviceSetting(
                deviceSetting.getId(),
                device.getId(),
                25,
                false,
                device.getCreatedAt().plusDays(7),
                device.getDeletedAt().plusDays(10),
                device.getUpdatedAt().plusDays(8),
                "New Updated By"
        );


        //when
        var response = callHttpMethod(HttpMethod.PUT,
                "/api/v1/devices/" + device.getId() + "/settings/" + deviceSetting.getId(),
                adminAccessToken,
                deviceSettingDtoMapper.toDto(deviceSetting),
                DeviceSettingDto.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        DeviceSettingDto body = response.getBody();
        Assertions.assertNull(body);
        //and
        DeviceSetting deviceSettingFromDb = deviceSettingService.findById(deviceSetting.getId());
        compareDeviceSettings(updatedDeviceSetting, deviceSettingFromDb);
    }

    @Test
    void device_owner_should_be_able_to_update_setting_for_device_he_owns() {
        //given
        User user = TestUserFactory.createDeviceOwner();
        Device device = TestDeviceFactory.createRandom();
        DeviceSetting deviceSetting = TestDeviceSettingFactory.createRandom();
        device.setOwnerId(user.getId());
        userService.save(user);
        deviceService.save(device);
        deviceSettingService.save(deviceSetting);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        DeviceSetting updatedDeviceSetting = new DeviceSetting(
                deviceSetting.getId(),
                device.getId(),
                25,
                false,
                device.getCreatedAt().plusDays(7),
                device.getDeletedAt().plusDays(10),
                device.getUpdatedAt().plusDays(8),
                "New Updated By"
        );

        //when
        var response = callHttpMethod(HttpMethod.PUT,
                "/api/v1/devices/" + device.getId() + "/settings/" + deviceSetting.getId(),
                token,
                deviceSettingDtoMapper.toDto(deviceSetting),
                DeviceSettingDto.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        DeviceSettingDto body = response.getBody();
        Assertions.assertNull(body);
        //and
        DeviceSetting deviceSettingFromDb = deviceSettingService.findById(deviceSetting.getId());
        compareDeviceSettings(updatedDeviceSetting, deviceSettingFromDb);
    }

    @Test
    void device_owner_should_not_be_able_to_update_setting_for_device_he_does_not_own() {
        //given
        User user = TestUserFactory.createDeviceOwner();
        Device device = TestDeviceFactory.createRandom();
        DeviceSetting deviceSetting = TestDeviceSettingFactory.createRandom();
        device.setOwnerId(user.getId() + "123");
        userService.save(user);
        deviceService.save(device);
        deviceSettingService.save(deviceSetting);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());
        Device updatedDevice = new Device(
                device.getId(),
                "Updated Name",
                "Updated Owner Id",
                device.getCreatedAt().plusDays(7),
                device.getDeletedAt().plusDays(10),
                device.getUpdatedAt().plusDays(8),
                "New Updated By"
        );

        DeviceSetting updatedDeviceSetting = new DeviceSetting(
                deviceSetting.getId(),
                "Updated DeviceId",
                25,
                false,
                device.getCreatedAt().plusDays(7),
                device.getDeletedAt().plusDays(10),
                device.getUpdatedAt().plusDays(8),
                "New Updated By"
        );

        //when
        var response = callHttpMethod(HttpMethod.PUT,
                "/api/v1/devices/" + device.getId() + "/settings/" + deviceSetting.getId(),
                token,
                deviceSettingDtoMapper.toDto(deviceSetting),
                DeviceSettingDto.class);

        //then
        Assertions.assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    @Test
    void admin_should_be_able_to_delete_device_setting() {
        //given
        String adminAccessToken = getTokenForAdmin();
        Device device = TestDeviceFactory.createRandom();
        DeviceSetting deviceSetting = TestDeviceSettingFactory.createRandom();
        deviceService.save(device);
        deviceSettingService.save(deviceSetting);

        //when
        var response = callHttpMethod(HttpMethod.DELETE,
                "/api/v1/devices/" + device.getId() + "/settings/" + deviceSetting.getId(),
                adminAccessToken,
                deviceSettingDtoMapper.toDto(deviceSetting),
                Void.class);

        //then
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void device_owner_should_not_be_able_to_delete_device_setting() {
        //given
        User user = TestUserFactory.createDeviceOwner();
        Device device = TestDeviceFactory.createRandom();
        DeviceSetting deviceSetting = TestDeviceSettingFactory.createRandom();
        deviceService.save(device);
        userService.save(user);
        deviceSettingService.save(deviceSetting);
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        //when
        var response = callHttpMethod(HttpMethod.DELETE,
                "/api/v1/devices/" + device.getId() + "/settings/" + deviceSetting.getId(),
                token,
                deviceSettingDtoMapper.toDto(deviceSetting),
                ErrorResponse.class);

        //then
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    private void compareDeviceSettings(DeviceSetting model, DeviceSetting tested) {
        assertNotNull(tested);
        assertEquals(model.getId(), tested.getId());
        assertEquals(model.getDeviceId(), tested.getDeviceId());
        assertEquals(model.getMeasurementPeriod(), tested.getMeasurementPeriod());
        assertEquals(model.isMeasurementEnabled(), tested.isMeasurementEnabled());isMeasurementEnabled
        assertEquals(model.getCreatedAt().toLocalDateTime(), tested.getCreatedAt().toLocalDateTime());
        assertEquals(model.getDeletedAt().toLocalDateTime(), tested.getDeletedAt().toLocalDateTime());
        assertEquals(model.getUpdatedAt().toLocalDateTime(), tested.getUpdatedAt().toLocalDateTime());
        assertEquals(model.getUpdatedBy(), tested.getUpdatedBy());
    }


}