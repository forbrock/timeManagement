package com.spring.project.dto;

import com.spring.project.model.enums.ActivityState;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserActivityDto {
    // TODO: check required fields before using
    private Long userId;
    private Long activityId;
    private ActivityState state;
    private LocalDateTime created;
    private LocalDateTime accepted;
}
