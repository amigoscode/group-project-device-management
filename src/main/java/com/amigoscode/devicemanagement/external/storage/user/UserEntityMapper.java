package com.amigoscode.devicemanagement.external.storage.user;

import com.amigoscode.devicemanagement.domain.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    UserEntity toEntity(User domain);

    User toDomain(UserEntity entity);

}
