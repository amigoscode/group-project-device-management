package com.amigoscode.devicemanagement.external.storage.measurement;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableScan
@EnableScanCount
interface DynamoMeasurementRepository extends PagingAndSortingRepository<MeasurementEntity, String> {

    Optional<MeasurementEntity> findById(String id);

    Page<MeasurementEntity> findAllByDeviceId(Pageable pageable, String deviceId);

    MeasurementEntity save(MeasurementEntity entity);

    void deleteById(String id);
}
