package com.spring.project.dto;

import com.spring.project.additional.validation.PasswordMatches;
import com.spring.project.additional.validation.ValidEmail;
import com.spring.project.model.enums.Role;
import com.spring.project.model.enums.State;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches
public class UserRegisterDto {
    @NotEmpty(message = "first name shouldn't be empty")
    private String firstName;

    @NotEmpty(message = "last name shouldn't be empty")
    private String lastName;

    @NotEmpty(message = "email shouldn't be empty")
    @ValidEmail(message = "not valid email")
    private String email;

    @NotEmpty(message = "password shouldn't be empty")
    private String password;

    @NotEmpty(message = "please confirm your password")
    private String matchingPassword;

    private Role role = Role.USER;

    private State state = State.ACTIVE;

    private final LocalDateTime created = LocalDateTime.now();
}
