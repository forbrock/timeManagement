package com.spring.project.repository;

import com.spring.project.model.TimeLog;
import com.spring.project.model.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeLogRepository extends JpaRepository<TimeLog, Long> {
    List<TimeLog> findByUserActivityId(Long id);
    List<TimeLog> findByUserActivityIn(List<UserActivity> activities);
}
