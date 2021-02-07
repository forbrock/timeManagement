package com.spring.project.dto;

import com.spring.project.model.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityDto {
    private Long id;
    private String name;
    private State state;
    private LocalDateTime created;
    private LocalDateTime lastModified;
}
