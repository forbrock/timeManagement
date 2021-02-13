package com.spring.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @NotEmpty(message = "{valid.login.not.empty}")
    private String email;

    @NotEmpty(message = "{valid.login.not.empty}")
    private String password;
}
