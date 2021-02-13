package com.spring.project.service;

import com.spring.project.dto.ActivityDto;
import com.spring.project.exceptions.ActivityAlreadyExistException;
import com.spring.project.model.Activity;
import com.spring.project.model.User;
import com.spring.project.model.UserActivity;
import com.spring.project.model.enums.ActivityState;
import com.spring.project.repository.ActivityRepository;
import com.spring.project.repository.UserActivityRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
@Service
public class UserActivityService {
    private UserActivityRepository userActivityRepository;
    private UserService userService;
    private SecurityService securityService;
    private ActivityRepository activityRepository;

    @Autowired
    public UserActivityService(UserActivityRepository userActivityRepository,
                               ActivityRepository activityRepository,
                               SecurityService securityService,
                               UserService userService) {
        this.userActivityRepository = userActivityRepository;
        this.activityRepository = activityRepository;
        this.securityService = securityService;
        this.userService = userService;
    }

    public Long deleteById(long id) {
        try {
            userActivityRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            log.info("Deleted activity with id: " + id);
        }
        return id;
    }

    public List<UserActivity> getRequestedActivities() {
        return userActivityRepository.findByState(ActivityState.REQUESTED);
    }

    @Transactional
    public UserActivity requestActivity(long activityId) throws ActivityAlreadyExistException {
        User user = securityService.getCurrentLoggedUser();
        Activity activity = activityRepository.findById(activityId).orElseThrow(() ->
                new NoSuchElementException("No such activity with id: " + activityId));

        UserActivity userActivity = UserActivity.builder()
                .user(user)
                .activity(activity)
                .state(ActivityState.REQUESTED)
                .build();

        try {
            userActivityRepository.save(userActivity);
        } catch (Exception e) {
            log.warn("Duplicated request for activity: id {}, name {}",
                    activity.getId(), activity.getName());
            throw new ActivityAlreadyExistException("Duplicated request for activity");
        }
        log.info("New activity requested: user {}, category {}, activity {}",
                user.getEmail(), activity.getCategory(), activity.getName());
        return userActivity;
    }
}
