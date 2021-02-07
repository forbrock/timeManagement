package com.spring.project.dto;

import com.spring.project.model.enums.Role;
import com.spring.project.model.enums.State;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private State state;
    private LocalDateTime created;
    private LocalDateTime lastModified;
}
