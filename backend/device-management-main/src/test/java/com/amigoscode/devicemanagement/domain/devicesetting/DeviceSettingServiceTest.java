package com.amigoscode.devicemanagement.domain.devicesetting;

import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;
import com.amigoscode.devicemanagement.external.storage.devicesetting.DeviceSettingAlreadyExistsException;
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
public class DeviceSettingServiceTest {

    @Mock
    private DeviceSettingRepository deviceSettingRepository;

    @InjectMocks
    private DeviceSettingService deviceSettingService;

    private final DeviceSetting fakeDeviceSetting = new DeviceSetting(
            "1234",
            "ID24",
            30,
            true,
            ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
            ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
            ZonedDateTime.of(2023, 2, 22, 12, 40, 00, 0, ZoneId.of("UTC")),
            "updatedBy"
    );

    @Test
    void update_method_should_not_throw_exception() {
        // Expect
        Assertions.assertDoesNotThrow(() -> deviceSettingService.update(fakeDeviceSetting));
    }

    @Test
    void save_method_should_return_saved_device_setting_when_device_setting_does_not_exist() {
        Mockito.when(deviceSettingRepository.save(
                fakeDeviceSetting
        )).thenReturn(fakeDeviceSetting);

        //when
        DeviceSetting savedDeviceSetting = deviceSettingService.save(fakeDeviceSetting);

        //then
        Assertions.assertNotNull(savedDeviceSetting);
        Assertions.assertEquals(fakeDeviceSetting.getId(), savedDeviceSetting.getId());
        Assertions.assertEquals(fakeDeviceSetting.getDeviceId(), savedDeviceSetting.getDeviceId());
        Assertions.assertEquals(fakeDeviceSetting.getIsMeasurementEnabled(), savedDeviceSetting.getIsMeasurementEnabled());
        Assertions.assertEquals(fakeDeviceSetting.getMeasurementPeriod(), savedDeviceSetting.getMeasurementPeriod());
        Assertions.assertEquals(fakeDeviceSetting.getCreatedAt(), savedDeviceSetting.getCreatedAt());
        Assertions.assertEquals(fakeDeviceSetting.getUpdatedAt(), savedDeviceSetting.getUpdatedAt());
        Assertions.assertEquals(fakeDeviceSetting.getDeletedAt(), savedDeviceSetting.getDeletedAt());
        Assertions.assertEquals(fakeDeviceSetting.getUpdatedBy(), savedDeviceSetting.getUpdatedBy());
    }

    @Test
    void save_method_should_throw_device_setting_already_exist_exception_when_device_setting_exist() {
        Mockito.when(deviceSettingRepository.save(
                fakeDeviceSetting
        )).thenThrow(new DeviceSettingAlreadyExistsException());
        //when
        //then
        Assertions.assertThrows(DeviceSettingAlreadyExistsException.class,
                ()-> deviceSettingService.save(fakeDeviceSetting));
    }

    @Test
    void find_by_deviceid_method_should_return_founded_device_setting_when_deviceid_exist() {
        Mockito.when(deviceSettingRepository.findByDeviceId(fakeDeviceSetting.getDeviceId())).thenReturn(Optional.of(fakeDeviceSetting));

        //when
        DeviceSetting foundedDeviceSetting = deviceSettingService.findByDeviceId(fakeDeviceSetting.getDeviceId());

        //then
        Assertions.assertNotNull(foundedDeviceSetting);
        Assertions.assertEquals(fakeDeviceSetting.getId(), foundedDeviceSetting.getId());
        Assertions.assertEquals(fakeDeviceSetting.getDeviceId(), foundedDeviceSetting.getDeviceId());
        Assertions.assertEquals(fakeDeviceSetting.getIsMeasurementEnabled(), foundedDeviceSetting.getIsMeasurementEnabled());
        Assertions.assertEquals(fakeDeviceSetting.getMeasurementPeriod(), foundedDeviceSetting.getMeasurementPeriod());
        Assertions.assertEquals(fakeDeviceSetting.getCreatedAt(), foundedDeviceSetting.getCreatedAt());
        Assertions.assertEquals(fakeDeviceSetting.getUpdatedAt(), foundedDeviceSetting.getUpdatedAt());
        Assertions.assertEquals(fakeDeviceSetting.getDeletedAt(), foundedDeviceSetting.getDeletedAt());
        Assertions.assertEquals(fakeDeviceSetting.getUpdatedBy(), foundedDeviceSetting.getUpdatedBy());

    }

    @Test
    void find_by_deviceid_method_should_throw_device_setting_not_found_exception_when_deviceid_does_not_exist() {
        Mockito.when(deviceSettingRepository.findByDeviceId(fakeDeviceSetting.getDeviceId())).thenReturn(Optional.empty());

        //when
        //then
        Assertions.assertThrows(DeviceSettingNotFoundException.class,
                ()-> deviceSettingService.findByDeviceId(fakeDeviceSetting.getDeviceId()));
    }

    @Test
    void find_by_id_method_should_return_founded_device_settimg_when_device_setting_exist() {
        Mockito.when(deviceSettingRepository.findById(fakeDeviceSetting.getId())).thenReturn(Optional.of(fakeDeviceSetting));

        //when
        DeviceSetting foundedDeviceSetting = deviceSettingService.findById(fakeDeviceSetting.getId());

        //then
        Assertions.assertNotNull(foundedDeviceSetting);
        Assertions.assertEquals(fakeDeviceSetting.getId(), foundedDeviceSetting.getId());
        Assertions.assertEquals(fakeDeviceSetting.getDeviceId(), foundedDeviceSetting.getDeviceId());
        Assertions.assertEquals(fakeDeviceSetting.getIsMeasurementEnabled(), foundedDeviceSetting.getIsMeasurementEnabled());
        Assertions.assertEquals(fakeDeviceSetting.getMeasurementPeriod(), foundedDeviceSetting.getMeasurementPeriod());
        Assertions.assertEquals(fakeDeviceSetting.getCreatedAt(), foundedDeviceSetting.getCreatedAt());
        Assertions.assertEquals(fakeDeviceSetting.getUpdatedAt(), foundedDeviceSetting.getUpdatedAt());
        Assertions.assertEquals(fakeDeviceSetting.getDeletedAt(), foundedDeviceSetting.getDeletedAt());
        Assertions.assertEquals(fakeDeviceSetting.getUpdatedBy(), foundedDeviceSetting.getUpdatedBy());
    }

    @Test
    void find_by_id_method_should_throw_device_setting_not_found_exception_when_device_settimg_does_not_exist() {
        Mockito.when(deviceSettingRepository.findById(fakeDeviceSetting.getId())).thenReturn(Optional.empty());

        //when
        //then
        Assertions.assertThrows(DeviceSettingNotFoundException.class,
                ()-> deviceSettingService.findById(fakeDeviceSetting.getId()));
    }
}

