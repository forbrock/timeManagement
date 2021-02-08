package com.spring.project.dto.mapper;

import com.spring.project.dto.LoginDto;
import com.spring.project.dto.UserDto;
import com.spring.project.dto.RegistrationDto;
import com.spring.project.dto.UserActivityDto;
import com.spring.project.model.User;
import com.spring.project.model.UserActivity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userDtoToUser(UserDto userDto);
    UserDto userToUserDto(User user);
    List<UserDto> mapUserToUserDto(List<User> users);
    List<UserActivityDto> mapUserActivityToUserActivityDto(List<UserActivity> activities);
    User regDtoToUser(RegistrationDto regDto);
    User loginDtoToUser(LoginDto loginDto);
}
