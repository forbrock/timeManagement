package com.spring.project.dto;

import com.spring.project.model.Activity;
import com.spring.project.model.User;
import com.spring.project.model.enums.ActivityState;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserActivityDto {
    private Long activityId;
    private Activity activity;
    private User user;
    private ActivityState state;
}
