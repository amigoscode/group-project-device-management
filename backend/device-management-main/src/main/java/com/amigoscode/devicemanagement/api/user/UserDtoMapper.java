package com.amigoscode.devicemanagement.api.user;

import com.amigoscode.devicemanagement.domain.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface UserDtoMapper {

    @Mapping(target="password", constant = "######")
    UserDto toDto(User domain);

    User toDomain(UserDto dto);
}