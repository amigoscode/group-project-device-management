package com.amigoscode.devicemanagement.domain.device;

import com.amigoscode.devicemanagement.domain.device.model.Device;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    private final Device fakeDevice = new Device(
            "ID28",
            "deviceName",
            "ownerId",
            ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
            ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
            ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
            "updatedBy"
    );

    @Test
    void update_method_should_not_throw_exception() {
        // Expect
        Assertions.assertDoesNotThrow(() -> deviceService.update(fakeDevice));
    }

    @Test
    void save_method_should_throw_device_already_exist_exception_when_device_exist() {
        Mockito.when(deviceRepository.save(
                fakeDevice
        )).thenThrow(new DeviceAlreadyExistsException());
        //when
        //then
        Assertions.assertThrows(DeviceAlreadyExistsException.class,
                ()-> deviceService.save(fakeDevice));
    }

    @Test
    void find_by_name_method_should_return_founded_device_when_device_exist() {
        Mockito.when(deviceRepository.findByName(fakeDevice.getName())).thenReturn(Optional.of(fakeDevice));

        //when
        Device foundedDevice = deviceService.findByName(fakeDevice.getName());

        //then
        Assertions.assertNotNull(foundedDevice);
        Assertions.assertEquals(fakeDevice.getId(), foundedDevice.getId());
        Assertions.assertEquals(fakeDevice.getName(), foundedDevice.getName());
        Assertions.assertEquals(fakeDevice.getOwnerId(), foundedDevice.getOwnerId());
        Assertions.assertEquals(fakeDevice.getCreatedAt(), foundedDevice.getCreatedAt());
        Assertions.assertEquals(fakeDevice.getUpdatedAt(), foundedDevice.getUpdatedAt());
        Assertions.assertEquals(fakeDevice.getDeletedAt(), foundedDevice.getDeletedAt());
        Assertions.assertEquals(fakeDevice.getUpdatedBy(), foundedDevice.getUpdatedBy());

    }

    @Test
    void find_by_name_method_should_throw_device_not_found_exception_when_device_does_not_exist() {
        Mockito.when(deviceRepository.findByName(fakeDevice.getName())).thenReturn(Optional.empty());

        //when
        //then
        Assertions.assertThrows(DeviceNotFoundException.class,
                ()-> deviceService.findByName(fakeDevice.getName()));
    }

    @Test
    void find_by_id_method_should_return_founded_device_when_device_exist() {
        Mockito.when(deviceRepository.findById(fakeDevice.getId())).thenReturn(Optional.of(fakeDevice));

        //when
        Device foundedDevice = deviceService.findById(fakeDevice.getId());

        //then
        Assertions.assertNotNull(foundedDevice);
        Assertions.assertEquals(fakeDevice.getId(), foundedDevice.getId());
        Assertions.assertEquals(fakeDevice.getName(), foundedDevice.getName());
        Assertions.assertEquals(fakeDevice.getOwnerId(), foundedDevice.getOwnerId());
        Assertions.assertEquals(fakeDevice.getCreatedAt(), foundedDevice.getCreatedAt());
        Assertions.assertEquals(fakeDevice.getUpdatedAt(), foundedDevice.getUpdatedAt());
        Assertions.assertEquals(fakeDevice.getDeletedAt(), foundedDevice.getDeletedAt());
        Assertions.assertEquals(fakeDevice.getUpdatedBy(), foundedDevice.getUpdatedBy());
    }

    @Test
    void find_by_id_method_should_throw_device_not_found_exception_when_device_does_not_exist() {
        Mockito.when(deviceRepository.findById(fakeDevice.getId())).thenReturn(Optional.empty());

        //when
        //then
        Assertions.assertThrows(DeviceNotFoundException.class,
                ()-> deviceService.findById(fakeDevice.getId()));
    }

}