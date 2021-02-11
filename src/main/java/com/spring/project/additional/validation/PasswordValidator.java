package com.spring.project.additional.validation;

import com.spring.project.dto.RegistrationDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PasswordValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationDto user = (RegistrationDto) target;
        if (!user.getMatchingPassword().equals(user.getPassword())) {
            errors.rejectValue("matchingPassword", "valid.password.matching");
        }
    }
}
