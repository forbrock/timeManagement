package com.spring.project.service;

import com.spring.project.dto.UserActivityDto;
import com.spring.project.dto.mapper.UserMapper;
import com.spring.project.repository.ActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ActivityService {
    private ActivityRepository activityRepository;
    private UserMapper mapper;

    @Autowired
    public ActivityService(ActivityRepository activityRepository, UserMapper mapper) {
        this.activityRepository = activityRepository;
        this.mapper = mapper;
    }

    public List<UserActivityDto> getUserActivities(long id) {
        return null;
    }
}
