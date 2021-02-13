package com.spring.project.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;

    @NotEmpty(message = "{valid.reg.not_empty}")
    @Size(min = 2, max = 30, message = "{valid.name.size}")
    private String firstName;

    @NotEmpty(message = "{valid.reg.not_empty}")
    @Size(min = 2, max = 30, message = "{valid.name.size}")
    private String lastName;

    @ToString.Exclude
    @Email(message = "{valid.reg.email}")
    @NotEmpty(message = "{valid.reg.not_empty}")
    @Size(min = 2, max = 25, message = "{valid.email.size}")
    private String email;

    @ToString.Exclude
    @NotEmpty(message = "{valid.reg.not_empty}")
    private String password;

    private Boolean enabled;
}
