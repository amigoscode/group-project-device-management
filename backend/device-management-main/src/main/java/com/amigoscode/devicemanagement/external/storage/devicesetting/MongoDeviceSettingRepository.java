package com.amigoscode.devicemanagement.external.storage.devicesetting;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoDeviceSettingRepository extends MongoRepository<DeviceSettingEntity, String> {
    
    Optional<DeviceSettingEntity> findByDeviceId(String deviceId);

}
