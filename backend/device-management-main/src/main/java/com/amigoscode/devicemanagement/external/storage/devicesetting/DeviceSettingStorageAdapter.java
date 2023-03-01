package com.amigoscode.devicemanagement.external.storage.devicesetting;


import com.amigoscode.devicemanagement.domain.devicesetting.DeviceSettingRepository;
import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Log
@Component
public class DeviceSettingStorageAdapter implements DeviceSettingRepository {
    private final  MongoDeviceSettingRepository deviceSettingRepository;

    private final  DeviceSettingEntityMapper mapper;

    @Override
    public void update(DeviceSetting deviceSetting) {
        deviceSettingRepository.findById(deviceSetting.getId()).ifPresent(deviceSettingEntity -> deviceSettingRepository.save(mapper.toEntity(deviceSetting)));
    }

    @Override
    public Optional<DeviceSetting> findById(String id) {
        return deviceSettingRepository.findById(id).map((mapper::toDomain));
    }

    @Override
    public Optional<DeviceSetting> findByDeviceId(String deviceId) {
        return deviceSettingRepository.findById(deviceId).map((mapper::toDomain));
    }
}
