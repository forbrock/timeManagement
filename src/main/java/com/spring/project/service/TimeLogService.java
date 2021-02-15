package com.spring.project.service;

import com.spring.project.exceptions.TimeIsNotValidException;
import com.spring.project.model.TimeLog;
import com.spring.project.model.UserActivity;
import com.spring.project.repository.TimeLogRepository;
import com.spring.project.repository.UserActivityRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

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
}
