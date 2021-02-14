package com.spring.project.service;

import com.spring.project.dto.ActivityDto;
import com.spring.project.dto.mapper.ActivityMapper;
import com.spring.project.exceptions.ActivityAlreadyExistException;
import com.spring.project.model.Activity;
import com.spring.project.model.User;
import com.spring.project.model.UserActivity;
import com.spring.project.repository.ActivityRepository;
import com.spring.project.repository.UserActivityRepository;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Log4j2
@Service
public class ActivityService {
    private ActivityRepository activityRepository;
    private ActivityMapper mapper;
    private SecurityService securityService;
    private UserActivityRepository userActivityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository,
                           ActivityMapper mapper,
                           SecurityService securityService,
                           UserActivityRepository userActivityRepository) {
        this.activityRepository = activityRepository;
        this.securityService = securityService;
        this.mapper = mapper;
        this.userActivityRepository = userActivityRepository;
    }

    @Transactional
    public Activity create(ActivityDto activityDto)
            throws ActivityAlreadyExistException, SQLException {

        Activity activity = mapper.dtoToActivity(activityDto);
        return activityRepository.save(activity);
    }

    public List<ActivityDto> getAll() {
        return mapper.mapActivityToActivityDto(activityRepository.findAll());
    }

    @Transactional
    public Activity update(ActivityDto activityDto) {
        Activity activity = activityRepository.findById(activityDto.getId()).orElseThrow(() ->
                new NoSuchElementException(
                        String.format("Can not update activity '%s'. Not found!",
                                activityDto.getName())));
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

    public Activity getById(long id) {
        return activityRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("No such activity was found"));
    }
}
