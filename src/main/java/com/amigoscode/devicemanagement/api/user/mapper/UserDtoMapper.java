package com.amigoscode.devicemanagement.api.user.mapper;

import com.amigoscode.devicemanagement.api.user.dto.UserDto;
import com.amigoscode.devicemanagement.domain.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    @Mapping(target="password", constant = "######")
    UserDto toDto(User domain);

    User toDomain(UserDto dto);
}