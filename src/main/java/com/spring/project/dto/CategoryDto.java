package com.spring.project.dto;

import com.spring.project.model.enums.State;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private Long id;
    private String name;
    private State state;
    private LocalDateTime created;
    private LocalDateTime lastModified;
}
