package com.amigoscode.devicemanagement.external.storage.device;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface MongoDeviceRepository extends MongoRepository<DeviceEntity, String> {

    Optional<DeviceEntity> findByName(String name);

    Optional<DeviceEntity> findById(String ownerId);

    Page<DeviceEntity> findAllByOwnerId(Pageable pageable, String userId);
}
