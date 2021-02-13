package com.spring.project.dto.mapper;

import com.spring.project.dto.UserActivityDto;
import com.spring.project.model.UserActivity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserActivityMapper {
    List<UserActivity> mapDtoToUserActivity(List<UserActivityDto> activities);
}
