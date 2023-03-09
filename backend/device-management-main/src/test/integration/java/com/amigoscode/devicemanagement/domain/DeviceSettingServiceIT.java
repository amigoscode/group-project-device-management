package com.amigoscode.devicemanagement.domain;

import com.amigoscode.devicemanagement.BaseIT;
import com.amigoscode.devicemanagement.TestDeviceSettingFactory;
import com.amigoscode.devicemanagement.domain.devicesetting.DeviceSettingService;
import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DeviceSettingServiceIT extends BaseIT {

    @Autowired
    DeviceSettingService service;


    @Test
    void add_device_setting_test() {
        //given
        DeviceSetting deviceSetting = TestDeviceSettingFactory.createDeviceSetting();
        service.save(deviceSetting);

        //when
        DeviceSetting readDeviceSetting = service.findById(deviceSetting.getId());

        //then
        Assertions.assertEquals(deviceSetting.getId(), readDeviceSetting.getId());
        Assertions.assertEquals(deviceSetting.getDeviceId(), readDeviceSetting.getDeviceId());
    }

    @Test
    void get_id_should_return_correct_device_setting() {
        //given
        DeviceSetting deviceSetting1 = TestDeviceSettingFactory.createDeviceSetting();
        DeviceSetting deviceSetting2 = TestDeviceSettingFactory.createDeviceSetting();
        DeviceSetting deviceSetting3 = TestDeviceSettingFactory.createDeviceSetting();
        service.save(deviceSetting1);
        service.save(deviceSetting2);
        service.save(deviceSetting3);

        //when
        DeviceSetting readDeviceSetting= service.findById(deviceSetting3.getId());

        //then
        Assertions.assertEquals(deviceSetting3.getId(), readDeviceSetting.getId());
        Assertions.assertEquals(deviceSetting3.getDeviceId(), readDeviceSetting.getDeviceId());
        Assertions.assertEquals(deviceSetting3.getMeasurementPeriod(), readDeviceSetting.getMeasurementPeriod());
        Assertions.assertEquals(deviceSetting3.getIsMeasurementEnabled(), readDeviceSetting.getIsMeasurementEnabled());

    }
}
