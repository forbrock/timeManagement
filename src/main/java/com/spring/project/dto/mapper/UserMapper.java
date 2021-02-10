package com.spring.project.dto.mapper;

import com.spring.project.dto.*;
import com.spring.project.model.User;
import com.spring.project.model.UserActivity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userDtoToUser(UserDto userDto);
    UserDto userToUserDto(User user);
    List<UserDto> mapUserToUserDto(List<User> users);
    List<UserActivityDto> mapUserActivityToUserActivityDto(List<UserActivity> activities);

    @Mapping(target = "enabled", constant = "true")
    User regDtoToUser(RegistrationDto regDto);

    @Mapping(target = "enabled", constant = "true")
    User loginDtoToUser(LoginDto loginDto);

    User updateDtoToUser(UpdateUserDto updateUserDto);
}
