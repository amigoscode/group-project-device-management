package com.amigoscode.weatherstationsimulator.domain.device;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    private final Device fakeDevice = new Device(
            "deviceId",
            "ownerId",
            "name"
    );

    @Test
    void save_device_method_should_not_throw_exception() {
        // Expect
        Assertions.assertDoesNotThrow(() -> deviceService.saveDevice(fakeDevice));
    }

    @Test
    void get_device_method_should_not_throw_exception() {
        // Expect
        Assertions.assertDoesNotThrow(() -> deviceService.getDevice());
    }

    @Test
    void save_method_should_return_saved_device() {
        Mockito.when(deviceRepository.saveDevice(
                fakeDevice
        )).thenReturn(fakeDevice);

        //when
        Device savedDevice = deviceService.saveDevice(fakeDevice);

        //then
        Assertions.assertNotNull(savedDevice);
        Assertions.assertEquals(fakeDevice.getId(), savedDevice.getId());
        Assertions.assertEquals(fakeDevice.getOwnerId(), savedDevice.getOwnerId());
        Assertions.assertEquals(fakeDevice.getName(), savedDevice.getName());
    }


    @Test
    void get_device_method_should_return_device() {
        Mockito.when(deviceRepository.getDevice()).thenReturn(fakeDevice);

        //when
        Device device = deviceService.getDevice();

        //then
        Assertions.assertNotNull(device);
        Assertions.assertEquals(fakeDevice.getId(), device.getId());
        Assertions.assertEquals(fakeDevice.getOwnerId(), device.getOwnerId());
        Assertions.assertEquals(fakeDevice.getName(), device.getName());

    }

}