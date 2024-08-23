package com.example.task_management_system.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.task_management_system.dto.UserDto;
import com.example.task_management_system.entity.User;

@Mapper
public interface UserMapping {
    UserMapping INSTANCE = Mappers.getMapper(UserMapping.class);

    UserDto userToUserDto(User user);
}
