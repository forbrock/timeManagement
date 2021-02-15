package com.spring.project.repository;

import com.spring.project.model.UserActivity;
import com.spring.project.model.enums.ActivityState;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
    List<UserActivity> findByUserId(long id);
    List<UserActivity> findByState(ActivityState state);
}
