package com.spring.project.service;

import com.spring.project.exceptions.TimeIsNotValidException;
import com.spring.project.model.TimeLog;
import com.spring.project.model.UserActivity;
import com.spring.project.repository.TimeLogRepository;
import com.spring.project.repository.UserActivityRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Log4j2
@Service
public class TimeLogService {
    private TimeLogRepository timeLogRepository;
    private UserActivityRepository userActivityRepository;

    public TimeLogService(TimeLogRepository timeLogRepository,
                          UserActivityRepository userActivityRepository) {
        this.timeLogRepository = timeLogRepository;
        this.userActivityRepository = userActivityRepository;
    }

    @Transactional
    public TimeLog addNewTimePoint(long id, String time) {
        if (!isTimeValid(time)) {
            throw new TimeIsNotValidException("valid.index.time.add.failure");
        }

        double newTimePoint = Double.parseDouble(time);
        UserActivity ua = userActivityRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Bad request"));

        TimeLog timeLog = TimeLog.builder()
                .duration(newTimePoint)
                .userActivity(ua)
                .build();

        return timeLogRepository.save(timeLog);
    }

    private boolean isTimeValid(String time) {
        // TODO: add validation logic
        return true;
    }

    public double getActivityTimeLog(long id) {
        List<TimeLog> allEntries = timeLogRepository.findByUserActivityId(id)
                .stream()
                .filter(entry -> entry.getUserActivity().getId() == id)
                .collect(Collectors.toList());

        return allEntries.stream().mapToDouble(TimeLog::getDuration).sum();
    }

/*
    public double getActivityTimeLog(Model model) {
        if (model.getAttribute("userActivities") == null) {
            return 0;
        }
        List<UserActivity> activities =
                (List<UserActivity>) model.getAttribute("userActivities");
        return activities.stream()
                .filter(entry -> entry.get)
    }
*/
}
