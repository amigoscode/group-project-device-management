package com.amigoscode.weatherstationsimulator.domain.devicesetting;

import com.amigoscode.weatherstationsimulator.domain.device.Device;

public interface DeviceSettingRepository {
    DeviceSetting getDeviceSetting();

    DeviceSetting saveDeviceSetting(DeviceSetting deviceSetting);
}
