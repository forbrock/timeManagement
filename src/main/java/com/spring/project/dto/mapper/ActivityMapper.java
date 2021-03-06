package com.spring.project.dto.mapper;

import com.spring.project.dto.ActivityDto;
import com.spring.project.model.Activity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityMapper {
    List<ActivityDto> mapActivityToActivityDto(List<Activity> activities);

    @Mapping(ignore = true, target = "id")
    Activity dtoToActivity(ActivityDto activityDto);
}
