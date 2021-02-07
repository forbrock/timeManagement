package com.spring.project.service;

import com.spring.project.dto.ActivityDto;
import com.spring.project.dto.mapper.ActivityMapper;
import com.spring.project.repository.ActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ActivityService {
    private ActivityRepository activityRepository;
    private ActivityMapper mapper;

    @Autowired
    public ActivityService(ActivityRepository activityRepository, ActivityMapper mapper) {
        this.activityRepository = activityRepository;
        this.mapper = mapper;
    }

    public List<ActivityDto> getAllActivities() {
        return mapper.mapActivityToActivityDto(activityRepository.findAll());
    }
}
