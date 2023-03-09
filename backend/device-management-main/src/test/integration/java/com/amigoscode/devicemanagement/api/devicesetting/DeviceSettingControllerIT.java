package com.amigoscode.devicemanagement.api.devicesetting;

import com.amigoscode.devicemanagement.BaseIT;
import com.amigoscode.devicemanagement.domain.device.DeviceService;
import com.amigoscode.devicemanagement.domain.devicesetting.DeviceSettingService;
import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;
import com.amigoscode.devicemanagement.domain.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    void admin_should_be_able_to_get_information_about_device_setting() {
        //given
//        String adminAccessToken = getTokenForAdmin();
//        Device device = TestDeviceFactory.createDevice();
//        DeviceSetting deviceSetting = TestDeviceSettingFactory.createDeviceSetting();
//        deviceService.save(device);
//        deviceSetting.setDeviceId(device.getId());
//        deviceSettingService.save(deviceSetting);
//
//
//        //when
//        var response = callHttpMethod(HttpMethod.GET,
//                "/api/v1/devices/" + device.getId() + "/settings/" + deviceSetting.getId(),
//                adminAccessToken,
//                null,
//                DeviceSettingDto.class);
//
//        //then
//        DeviceSettingDto body = response.getBody();
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        //and
//        compareDeviceSettings(deviceSetting, deviceSettingDtoMapper.toDomain(body));
    }



    private void compareDeviceSettings(DeviceSetting model, DeviceSetting tested) {
        assertNotNull(tested);
        assertEquals(model.getId(), tested.getId());
        assertEquals(model.getDeviceId(), tested.getDeviceId());
        assertEquals(model.getMeasurementPeriod(), tested.getMeasurementPeriod());
        assertEquals(model.getIsMeasurementEnabled(), tested.getIsMeasurementEnabled());
        assertEquals(model.getCreatedAt().toLocalDateTime(), tested.getCreatedAt().toLocalDateTime());
        assertEquals(model.getDeletedAt().toLocalDateTime(), tested.getDeletedAt().toLocalDateTime());
        assertEquals(model.getUpdatedAt().toLocalDateTime(), tested.getUpdatedAt().toLocalDateTime());
        assertEquals(model.getUpdatedBy(), tested.getUpdatedBy());
    }


}
