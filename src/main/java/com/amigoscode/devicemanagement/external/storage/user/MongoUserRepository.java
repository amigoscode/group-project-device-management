package com.amigoscode.devicemanagement.external.storage.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoUserRepository extends MongoRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByName(String Name);
}
