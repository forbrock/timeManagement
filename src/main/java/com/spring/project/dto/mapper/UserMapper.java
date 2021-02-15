package com.spring.project.dto.mapper;

import com.spring.project.dto.RegistrationDto;
import com.spring.project.dto.UserDto;
import com.spring.project.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    List<UserDto> mapUserToUserDto(List<User> users);

    @Mapping(target = "enabled", constant = "true")
    User regDtoToUser(RegistrationDto regDto);
}
