package com.amigoscode.devicemanagement.api.devicesetting;

import com.amigoscode.devicemanagement.BaseIT;
import com.amigoscode.devicemanagement.TestDeviceFactory;
import com.amigoscode.devicemanagement.TestDeviceSettingFactory;
import com.amigoscode.devicemanagement.TestUserFactory;
import com.amigoscode.devicemanagement.domain.device.DeviceService;
import com.amigoscode.devicemanagement.domain.device.model.Device;
import com.amigoscode.devicemanagement.domain.devicesetting.DeviceSettingService;
import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;
import com.amigoscode.devicemanagement.domain.user.UserService;
import com.amigoscode.devicemanagement.domain.user.model.User;
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
    DeviceSettingService deviceSettingService;

    @Autowired
    DeviceSettingDtoMapper deviceSettingDtoMapper;

    @Test
    void admin_should_be_able_to_update_device_setting() {
        //given
        String adminAccessToken = getTokenForAdmin();
        Device device = TestDeviceFactory.createDevice();
        DeviceSetting deviceSetting = TestDeviceSettingFactory.createDeviceSetting();
        deviceService.save(device, "creatorId");
        DeviceSetting dfaultDeviceSetting = deviceSettingService.findByDeviceId(device.getId());
        deviceSetting.setId(dfaultDeviceSetting.getId());
        deviceSetting.setDeviceId(device.getId());
        deviceSettingService.update(deviceSetting, "updaterId");

        DeviceSetting updatedDeviceSetting = new DeviceSetting(
                deviceSetting.getId(),
                device.getId(),
                25,
                false,
                device.getCreatedAt(),
                device.getDeletedAt(),
                device.getUpdatedAt(),
                device.getUpdatedBy()
        );


        //when
        var response = callHttpMethod(HttpMethod.PUT,
                "/api/v1/devices/" + device.getId() + "/settings",
                adminAccessToken,
                deviceSettingDtoMapper.toDto(updatedDeviceSetting),
                DeviceSettingDto.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        DeviceSettingDto body = response.getBody();
        Assertions.assertNull(body);
        //and
        DeviceSetting deviceSettingFromDb = deviceSettingService.findByDeviceId(device.getId());
        Assertions.assertEquals(deviceSettingFromDb.getDeviceId(), updatedDeviceSetting.getDeviceId());
        Assertions.assertEquals(deviceSettingFromDb.getMeasurementPeriod(), updatedDeviceSetting.getMeasurementPeriod());
        Assertions.assertEquals(deviceSettingFromDb.getIsMeasurementEnabled(), updatedDeviceSetting.getIsMeasurementEnabled());
    }

    @Test
    void device_owner_should_be_able_to_update_setting_for_device_he_owns() {
        //given
        User user = TestUserFactory.createDeviceOwner();
        Device device = TestDeviceFactory.createDevice();
        DeviceSetting deviceSetting = TestDeviceSettingFactory.createDeviceSetting();
        device.setOwnerId(user.getId());
        userService.save(user);
        deviceService.save(device, "creatorId");
        DeviceSetting dfaultDeviceSetting = deviceSettingService.findByDeviceId(device.getId());
        deviceSetting.setId(dfaultDeviceSetting.getId());
        deviceSetting.setDeviceId(device.getId());
        deviceSettingService.update(deviceSetting, "updaterId");
        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());

        DeviceSetting updatedDeviceSetting = new DeviceSetting(
                deviceSetting.getId(),
                device.getId(),
                25,
                false,
                device.getCreatedAt(),
                device.getDeletedAt(),
                device.getUpdatedAt(),
                device.getUpdatedBy()
        );

        //when
        var response = callHttpMethod(HttpMethod.PUT,
                "/api/v1/devices/" + device.getId() + "/settings",
                token,
                deviceSettingDtoMapper.toDto(updatedDeviceSetting),
                DeviceSettingDto.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        DeviceSettingDto body = response.getBody();
        Assertions.assertNull(body);
        //and
        DeviceSetting deviceSettingFromDb = deviceSettingService.findByDeviceId(device.getId());
        Assertions.assertEquals(deviceSettingFromDb.getDeviceId(), updatedDeviceSetting.getDeviceId());
        Assertions.assertEquals(deviceSettingFromDb.getMeasurementPeriod(), updatedDeviceSetting.getMeasurementPeriod());
        Assertions.assertEquals(deviceSettingFromDb.getIsMeasurementEnabled(), updatedDeviceSetting.getIsMeasurementEnabled());
    }

    @Test
    void admin_should_be_able_to_get_information_about_device_setting() {
        //given
        String adminAccessToken = getTokenForAdmin();
        Device device = TestDeviceFactory.createDevice();
        deviceService.save(device, "creatorId");
        DeviceSetting deviceSetting = TestDeviceSettingFactory.createDeviceSetting();
        DeviceSetting dfaultDeviceSetting = deviceSettingService.findByDeviceId(device.getId());
        deviceSetting.setId(dfaultDeviceSetting.getId());
        deviceSetting.setDeviceId(device.getId());
        deviceSettingService.update(deviceSetting, "updaterId");

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/devices/" + device.getId() + "/settings",
                adminAccessToken,
                null,
                DeviceSettingDto.class);

        //then
        DeviceSettingDto body = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        compareDeviceSettings(deviceSetting, deviceSettingDtoMapper.toDomain(body));
    }

    @Test
    void device_owner_should_be_able_to_get_device_setting_information_about_device_he_owns() {
        //given
        User user = TestUserFactory.createDeviceOwner();
        userService.save(user);
        Device device = TestDeviceFactory.createDevice();
        device.setOwnerId(user.getId());
        deviceService.save(device, "creatorId");
        DeviceSetting deviceSetting = TestDeviceSettingFactory.createDeviceSetting();
        deviceSetting.setDeviceId(device.getId());
        DeviceSetting dfaultDeviceSetting = deviceSettingService.findByDeviceId(device.getId());
        deviceSetting.setId(dfaultDeviceSetting.getId());
        deviceSetting.setDeviceId(device.getId());
        deviceSettingService.update(deviceSetting, "updaterId");

        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());
        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/devices/" + device.getId() + "/settings",
                token,
                null,
                DeviceSettingDto.class);

        //then
        DeviceSettingDto body = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        compareDeviceSettings(deviceSetting, deviceSettingDtoMapper.toDomain(body));
    }

    @Test
    void device_owner_should_not_be_able_to_get_device_setting_information_about_device_he_does_not_own() {
        //given
        User user = TestUserFactory.createDeviceOwner();
        userService.save(user);
        Device device = TestDeviceFactory.createDevice();
        device.setOwnerId(user.getId() + "qwerty");
        deviceService.save(device, "creatorId");
        DeviceSetting deviceSetting = TestDeviceSettingFactory.createDeviceSetting();
        deviceSetting.setDeviceId(device.getId());
        DeviceSetting dfaultDeviceSetting = deviceSettingService.findByDeviceId(device.getId());
        deviceSetting.setId(dfaultDeviceSetting.getId());
        deviceSetting.setDeviceId(device.getId());
        deviceSettingService.update(deviceSetting, "updaterId");

        String token = getAccessTokenForUser(user.getEmail(), user.getPassword());


        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/devices/" + device.getId() + "/settings",
                token,
                null,
                DeviceSettingDto.class);

        //then
        Assertions.assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    @Test
    void device_owner_should_not_be_able_to_update_setting_for_device_he_does_not_own() {
        //given
        User user = TestUserFactory.createDeviceOwner();
        Device device = TestDeviceFactory.createDevice();
        DeviceSetting deviceSetting = TestDeviceSettingFactory.createDeviceSetting();
        device.setOwnerId(user.getId() + "123");
        userService.save(user);
        deviceService.save(device, "creatorId");
        DeviceSetting dfaultDeviceSetting = deviceSettingService.findByDeviceId(device.getId());
        deviceSetting.setId(dfaultDeviceSetting.getId());
        deviceSetting.setDeviceId(device.getId());
        deviceSettingService.update(deviceSetting, "updaterId");
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
                "/api/v1/devices/" + device.getId() + "/settings",
                token,
                deviceSettingDtoMapper.toDto(updatedDeviceSetting),
                DeviceSettingDto.class);

        //then
        Assertions.assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    private void compareDeviceSettings(DeviceSetting model, DeviceSetting tested) {
        assertNotNull(tested);
        assertEquals(model.getId(), tested.getId());
        assertEquals(model.getDeviceId(), tested.getDeviceId());
        assertEquals(model.getMeasurementPeriod(), tested.getMeasurementPeriod());
        assertEquals(model.getIsMeasurementEnabled(), tested.getIsMeasurementEnabled());
        assertEquals(model.getCreatedAt().toInstant(), tested.getCreatedAt().toInstant());
        assertEquals(model.getDeletedAt().toInstant(), tested.getDeletedAt().toInstant());
        assertEquals(model.getUpdatedAt().toInstant(), tested.getUpdatedAt().toInstant());
        assertEquals(model.getUpdatedBy(), tested.getUpdatedBy());
    }

}