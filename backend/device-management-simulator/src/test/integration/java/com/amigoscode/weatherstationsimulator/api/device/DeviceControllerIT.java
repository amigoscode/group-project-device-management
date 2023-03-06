package com.amigoscode.weatherstationsimulator.api.device;


import com.amigoscode.weatherstationsimulator.BaseIT;
import com.amigoscode.weatherstationsimulator.TestDeviceFactory;
import com.amigoscode.weatherstationsimulator.domain.device.Device;
import com.amigoscode.weatherstationsimulator.domain.device.DeviceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DeviceControllerIT extends BaseIT {


    @Autowired
    DeviceService deviceService;

    @Autowired
    DeviceDtoMapper deviceDtoMapper;


    @Test
    void user_should_be_able_to_get_information_about_device() {
        //given
        Device device = TestDeviceFactory.createRandom();
        deviceService.saveDevice(device);

        //when
        var response = callHttpMethod(HttpMethod.GET,
                "/api/v1/device",
                null,
                DeviceDto.class);

        //then
        DeviceDto body = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        compareDevices(device, deviceDtoMapper.toDomain(body));
    }

    @Test
    void user_should_be_able_to_update_information_about_device() {
        //given
        Device device = TestDeviceFactory.createRandom();
        deviceService.saveDevice(device);
        device.setDeviceId(device.getDeviceId() + "qwerty");
        device.setOwnerId(device.getOwnerId() + "qwerty");
        device.setName(device.getName() + "qwerty");

        //when
        var response = callHttpMethod(HttpMethod.PUT,
                "/api/v1/device",
                deviceDtoMapper.toDto(device),
                DeviceDto.class);

        //then
        DeviceDto body = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //and
        compareDevices(device, deviceDtoMapper.toDomain(body));
    }

    private void compareDevices(Device model, Device tested) {
        assertNotNull(tested);
        assertEquals(model.getDeviceId(), tested.getDeviceId());
        assertEquals(model.getName(), tested.getName());
        assertEquals(model.getOwnerId(), tested.getOwnerId());
    }

}
