package com.amigoscode.devicemanagement;

import com.amigoscode.devicemanagement.domain.devicesetting.DeviceSettingService;
import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@Log
public class DefaultDeviceSettings implements CommandLineRunner {
    private final DeviceSettingService deviceSettingService;

    public DefaultDeviceSettings(DeviceSettingService deviceSettingService){
        this.deviceSettingService = deviceSettingService;
    }

    private final DeviceSetting deviceSetting1 = new DeviceSetting(
            "4",
            "3",
            30,
            true,
            ZonedDateTime.now(),
            ZonedDateTime.now(),
            ZonedDateTime.now(),
            "Osagie");

    private final DeviceSetting deviceSetting2 = new DeviceSetting(
            "5",
            "4",
            30,
            false,
            ZonedDateTime.now(),
            ZonedDateTime.now(),
            ZonedDateTime.now(),
            "Rafal");

    @Override
    public void run(String... args){
        try {
            addDeviceSetting(deviceSetting1);
            addDeviceSetting(deviceSetting2);
        } catch (Exception ex) {
            log.warning("Devices Settings already exist");
        }
    }

    private void addDeviceSetting(DeviceSetting deviceSetting){
        deviceSettingService.save(deviceSetting);
    }
}
