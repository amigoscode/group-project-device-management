package com.amigoscode.devicemanagement.domain.devicesetting;

import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;

public interface DeviceSettingPublisher {
    void publish(DeviceSetting deviceSetting);
}
