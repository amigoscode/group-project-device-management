package com.amigoscode.devicemanagement.external.storage.rule;

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
interface DynamoRuleRepository extends PagingAndSortingRepository<RuleEntity, String> {

    RuleEntity save(RuleEntity entity);

    void deleteById(String id);

    Optional<RuleEntity> findById(String id);

    Optional<RuleEntity> findByName(String name);

    Page<RuleEntity> findAll(Pageable pageable);
}
