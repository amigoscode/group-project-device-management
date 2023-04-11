package com.amigoscode.weatherstationsimulator.domain.devicesetting;

import com.amigoscode.weatherstationsimulator.domain.device.Device;
import com.amigoscode.weatherstationsimulator.domain.device.DeviceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeviceSettingService {

    private final DeviceSettingRepository deviceSettingRepository;

    public DeviceSetting saveDeviceSetting(DeviceSetting deviceSetting) {
        return deviceSettingRepository.saveDeviceSetting(deviceSetting);
    }

    public DeviceSetting getDeviceSetting() {
        return deviceSettingRepository.getDeviceSetting();
    }

}
