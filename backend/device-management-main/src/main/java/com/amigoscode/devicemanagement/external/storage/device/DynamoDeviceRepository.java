package com.amigoscode.devicemanagement.external.storage.device;

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
interface DynamoDeviceRepository extends PagingAndSortingRepository<DeviceEntity, String> {

    Optional<DeviceEntity> findByName(String name);

    Optional<DeviceEntity> findById(String ownerId);

    Page<DeviceEntity> findAllByOwnerId(Pageable pageable, String userId);

    DeviceEntity save(DeviceEntity entity);

    void deleteById(String id);
}
