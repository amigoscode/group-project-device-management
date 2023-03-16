package com.amigoscode.devicemanagement.external.storage.user;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableScan
@EnableScanCount
interface DynamoUserRepository extends PagingAndSortingRepository<UserEntity, String> {

    Optional<UserEntity> findById(String id);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByName(String Name);

    UserEntity save(UserEntity entity);

    void deleteById(String id);
}
