package com.amigoscode.devicemanagement.domain.devicesetting;

import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;

import java.util.Optional;

public interface DeviceSettingRepository {

    DeviceSetting save(DeviceSetting deviceSetting);

    void update(DeviceSetting deviceSetting);

    Optional<DeviceSetting> findById(String id);

    Optional<DeviceSetting> findByDeviceId(String deviceId);

    void remove(String id);
}
