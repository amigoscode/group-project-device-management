package com.amigoscode.devicemanagement.external.storage.measurement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface MongoMeasurementRepository extends MongoRepository<MeasurementEntity, String> {

    Optional<MeasurementEntity> findById(String id);

    Page<MeasurementEntity> findAllByDeviceId(Pageable pageable, String deviceId);
}
