package com.amigoscode.devicemanagement.domain;

import com.amigoscode.devicemanagement.domain.user.DeviceService;
import com.amigoscode.devicemanagement.domain.user.model.Device;
import com.amigoscode.devicemanagement.BaseIT;
import com.amigoscode.devicemanagement.TestDeviceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DeviceServiceIT extends BaseIT {

    @Autowired
    DeviceService service;


    @Test
    void add_device_test() {
        //given
        Device device = TestDeviceFactory.createDevice();
        service.save(device);

        //when
        Device readDevice = service.findById(device.getId());

        //then
        Assertions.assertEquals(device.getId(), readUser.getId());
        Assertions.assertEquals(device.getName(), readUser.getName());
        Assertions.assertEquals(device.getOwnerId(), readUser.getOwnerId());
    }

    @Test
    void get_id_should_return_correct_device() {
        //given
        Device device1 = TestDeviceFactory.createDevice();
        Device device2 = TestDeviceFactory.createDevice();
        Device device3 = TestDeviceFactory.createDevice();
        service.save(device1);
        service.save(device2);
        service.save(device3);

        //when
        Device readUser = service.findById(device3.getId());

        //then
        Assertions.assertEquals(device3.getId(), readUser.getId());
        Assertions.assertEquals(device3.getName(), readUser.getName());
        Assertions.assertEquals(device3.getOwnerId(), readUser.getOwnerId());
    }
}