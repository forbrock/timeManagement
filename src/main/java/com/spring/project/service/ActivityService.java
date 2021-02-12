package com.spring.project.service;

import com.spring.project.dto.ActivityDto;
import com.spring.project.dto.mapper.ActivityMapper;
import com.spring.project.exceptions.ActivityAlreadyExistException;
import com.spring.project.model.Activity;
import com.spring.project.model.User;
import com.spring.project.model.UserActivity;
import com.spring.project.repository.ActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Slf4j
@Service
public class ActivityService {
    private ActivityRepository activityRepository;
    private ActivityMapper mapper;
    private SecurityService securityService;

    @Autowired
    public ActivityService(ActivityRepository activityRepository,
                           ActivityMapper mapper, SecurityService securityService) {
        this.activityRepository = activityRepository;
        this.securityService = securityService;
        this.mapper = mapper;
    }

    @Transactional
    public Activity create(ActivityDto activityDto)
            throws ActivityAlreadyExistException {

        Activity activity = mapper.dtoToActivity(activityDto);
        return activityRepository.save(activity);
    }

    public List<ActivityDto> getAll() {
        return mapper.mapActivityToActivityDto(activityRepository.findAll());
    }

    // TODO: don't forget to remove id from exception output
    @Transactional
    public Activity update(ActivityDto activityDto) {
        Activity activity = activityRepository.findById(activityDto.getId()).orElseThrow(() ->
                new UsernameNotFoundException("No such user with id: " + activityDto.getId()));
        String oldName = activity.getName();
        activity.setName(activityDto.getName());
        activity.setLastModified(LocalDateTime.now());

        log.info("Activity '{}' was modified to {}", oldName, activityDto.getName());
        return activityRepository.save(activity);
    }

    public Long deleteById(long id) {
        try {
            activityRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            log.info("Deleted activity with id: " + id);
        }
        return id;
    }

    // TODO: don't forget to remove id from exception output
    public Activity getById(long id) {
        return activityRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("No such activity was found, id: " + id));
    }

    public Set<UserActivity> getCurrentUserActivities() {
        final User currentLoggedUser = securityService.getCurrentLoggedUser();
        return null;
    }
}
