package com.amigoscode.devicemanagement.external.storage.measurement;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@EnableScan
@EnableScanCount
interface DynamoMeasurementRepository extends PagingAndSortingRepository<MeasurementEntity, MeasurementEntityId> {

    Optional<MeasurementEntity> findByMeasurementEntityId(MeasurementEntityId id);

    Page<MeasurementEntity> findAllByDeviceId(Pageable pageable, String deviceId);

    Page<MeasurementEntity> findByDeviceIdAndTimestampBetween(Pageable pageable, final String deviceId, final ZonedDateTime startTimestamp, final ZonedDateTime endTimestamp);

    MeasurementEntity save(MeasurementEntity entity);

    void deleteByMeasurementEntityId(MeasurementEntityId id);
}
