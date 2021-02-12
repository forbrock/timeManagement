package com.spring.project.repository;

import com.spring.project.model.Activity;
import com.spring.project.model.User;
import com.spring.project.model.UserActivity;
import com.spring.project.model.enums.ActivityState;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@Log4j2
@SpringBootTest
class UserActivityRepositoryTest {

    @Autowired
    private UserActivityRepository userActivityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @Test
    public void assignActivityForUserTest(){
        User user = userRepository.findById(2L).orElse(null);

        Activity activity = activityRepository.findById(6L).orElse(null);

        UserActivity userActivity = UserActivity.builder()
                .user(user)
                .activity(activity)
                .state(ActivityState.ASSIGNED)
                .build();

        try {
            userActivityRepository.save(userActivity);
        } catch (Exception e) {
            log.error("User {} not saved", user, e);
        }
        List<UserActivity> activities = userActivityRepository.findByUserId(2L);
        Activity test = activities.get(0).getActivity();
        assertEquals(activity, test);
    }
}