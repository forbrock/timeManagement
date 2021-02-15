package com.spring.project.service;

import com.spring.project.dto.UserActivityDto;
import com.spring.project.exceptions.ActivityAlreadyExistException;
import com.spring.project.model.Activity;
import com.spring.project.model.TimeLog;
import com.spring.project.model.User;
import com.spring.project.model.UserActivity;
import com.spring.project.model.enums.ActivityState;
import com.spring.project.repository.ActivityRepository;
import com.spring.project.repository.TimeLogRepository;
import com.spring.project.repository.UserActivityRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.*;

@Log4j2
@Service
public class UserActivityService {
    private UserActivityRepository userActivityRepository;
    private SecurityService securityService;
    private ActivityRepository activityRepository;
    private TimeLogRepository timeLogRepository;

    @Autowired
    public UserActivityService(UserActivityRepository userActivityRepository,
                               ActivityRepository activityRepository,
                               SecurityService securityService,
                               TimeLogRepository timeLogRepository) {
        this.userActivityRepository = userActivityRepository;
        this.activityRepository = activityRepository;
        this.securityService = securityService;
        this.timeLogRepository = timeLogRepository;
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

    @Transactional
    public UserActivity confirmRequest(long id) {
        UserActivity ua = userActivityRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format("User activity with id %d not found", id)));
        ua.setState(ActivityState.ACCEPTED);
        log.info("Accepted activity [User: {}, activity: {}]",
                ua.getUser().getEmail(), ua.getActivity().getName());
        return userActivityRepository.save(ua);
    }

    public Long deleteRequest(long id) {
        userActivityRepository.deleteById(id);
        return id;
    }

    public List<UserActivity> getCurrentUserActivities() {
        final User currentLoggedUser = securityService.getCurrentLoggedUser();
        return userActivityRepository.findByUserId(currentLoggedUser.getId());
    }

    public List<UserActivity> getUserActivitiesById(long id) {
        return userActivityRepository.findByUserId(id);
    }

    public List<UserActivityDto> combineUserActivities(List<UserActivity> userActivities) {
        List<TimeLog> timeLogList = timeLogRepository.findByUserActivityIn(userActivities);

        Map<Long, Double> totalSumOfEachUserActivity = timeLogList.stream()
                .collect(groupingBy(el -> el.getUserActivity().getId(),
                        summingDouble(TimeLog::getDuration)));

        return userActivities.stream()
                .map(el -> UserActivityDto.builder()
                        .id(el.getId())
                        .activity(el.getActivity())
                        .state(el.getState())
                        .duration(totalSumOfEachUserActivity.get(el.getId()))
                        .user(el.getUser())
                        .build()).collect(toList());
    }

    public List<UserActivity> getAll() {
        return userActivityRepository.findAll();
    }

    public Page<UserActivity> findAllPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return userActivityRepository.findAll(pageable);
    }
}
