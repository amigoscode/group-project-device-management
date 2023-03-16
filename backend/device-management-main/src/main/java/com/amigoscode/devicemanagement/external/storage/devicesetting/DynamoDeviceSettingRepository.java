package com.amigoscode.devicemanagement.external.storage.devicesetting;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableScan
@EnableScanCount
public interface DynamoDeviceSettingRepository extends PagingAndSortingRepository<DeviceSettingEntity, String> {

    Optional<DeviceSettingEntity> findById(String id);

    Optional<DeviceSettingEntity> findByDeviceId(String deviceId);

    DeviceSettingEntity save(DeviceSettingEntity entity);

    void deleteById(String id);
}
