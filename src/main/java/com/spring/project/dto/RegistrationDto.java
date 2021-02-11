package com.spring.project.dto;

import com.spring.project.additional.validation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationDto {
    @NotEmpty(message = "{valid.reg.not_empty}")
    @Size(min = 2, max = 30, message = "{valid.name.size}")
    private String firstName;

    @NotEmpty(message = "{valid.reg.not_empty}")
    @Size(min = 2, max = 30, message = "{valid.name.size}")
    private String lastName;

    @ValidEmail(message = "{valid.reg.email}")
    @NotEmpty(message = "{valid.reg.not_empty}")
    @Size(min = 5, max = 50, message = "{valid.email.size}")
    private String email;

    @NotEmpty(message = "{valid.reg.not_empty}")
    private String password;

    @NotEmpty(message = "{valid.reg.not_empty}")
    private String matchingPassword;
}
