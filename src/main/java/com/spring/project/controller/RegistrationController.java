package com.spring.project.controller;

import com.spring.project.validation.PasswordValidator;
import com.spring.project.dto.RegistrationDto;
import com.spring.project.exceptions.UserAlreadyExistException;
import com.spring.project.model.User;
import com.spring.project.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Log4j2
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private UserService userService;
    private PasswordValidator passwordValidator;

    @Autowired
    public RegistrationController(UserService userService, PasswordValidator passwordValidator) {
        this.userService = userService;
        this.passwordValidator = passwordValidator;
    }

    @GetMapping
    public String showRegistrationPage(@ModelAttribute("user") RegistrationDto regDto) {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid RegistrationDto regDto,
                                      BindingResult bindingResult, Model model) {
        passwordValidator.validate(regDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        try {
            User registered = userService.registerNewAccount(regDto);
            log.info("Account {} registered successfully", registered.getEmail());
        } catch (UserAlreadyExistException e) {
            bindingResult.rejectValue("email", "user.email",
                    "reg.login.not.unique");
            return "registration";
        }
        return "redirect:/";
    }
}
