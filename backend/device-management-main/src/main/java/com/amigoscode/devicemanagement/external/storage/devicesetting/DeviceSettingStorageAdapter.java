package com.amigoscode.devicemanagement.external.storage.devicesetting;

import com.amigoscode.devicemanagement.domain.devicesetting.DeviceSettingAlreadyExistsException;
import com.amigoscode.devicemanagement.domain.devicesetting.DeviceSettingRepository;
import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@RequiredArgsConstructor
@Log
@Component
class DeviceSettingStorageAdapter implements DeviceSettingRepository {

    private final  DynamoDeviceSettingRepository deviceSettingRepository;

    private final  DeviceSettingEntityMapper mapper;

    @Override
    public DeviceSetting save(DeviceSetting deviceSetting){
        try{
            DeviceSettingEntity saved = deviceSettingRepository.save(mapper.toEntity(deviceSetting));
            log.info("Saved device setting entity" + saved);
            return mapper.toDomain(saved);
        } catch (DuplicateKeyException ex) {
            log.warning("Device Setting " + deviceSetting.getId() + "already exists in db");
            throw new DeviceSettingAlreadyExistsException();

        }
    }
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
        return deviceSettingRepository.findByDeviceId(deviceId).map((mapper::toDomain));
    }
}
