package com.spring.project.dto;

import com.spring.project.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityDto {
    private Long id;

    @NotEmpty(message = "{valid.user.request.activity}")
    private String name;

    private Category category;
}
