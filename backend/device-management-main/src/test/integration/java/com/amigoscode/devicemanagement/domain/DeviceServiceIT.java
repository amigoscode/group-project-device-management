package com.amigoscode.devicemanagement.domain;

import com.amigoscode.devicemanagement.BaseIT;
import com.amigoscode.devicemanagement.TestDeviceFactory;
import com.amigoscode.devicemanagement.domain.device.DeviceService;
import com.amigoscode.devicemanagement.domain.device.model.Device;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DeviceServiceIT extends BaseIT {


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
        Assertions.assertEquals(device.getId(), readDevice.getId());
        Assertions.assertEquals(device.getName(), readDevice.getName());
        Assertions.assertEquals(device.getOwnerId(), readDevice.getOwnerId());
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
        Device readDevice= service.findById(device3.getId());

        //then
        Assertions.assertEquals(device3.getId(), readDevice.getId());
        Assertions.assertEquals(device3.getName(), readDevice.getName());
        Assertions.assertEquals(device3.getOwnerId(), readDevice.getOwnerId());
    }
}
