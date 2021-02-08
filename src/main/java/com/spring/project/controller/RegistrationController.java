package com.spring.project.controller;

import com.spring.project.dto.RegistrationDto;
import com.spring.project.exceptions.UserAlreadyExistException;
import com.spring.project.model.User;
import com.spring.project.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@Controller
@RequestMapping("/registration")
public class RegistrationController implements ErrorController {

    private UserService userService;
    private MessageSource messageSource;

    @Autowired
    public RegistrationController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String showRegistrationPage(@ModelAttribute("user") RegistrationDto regDto) {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid RegistrationDto regDto,
                                      BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        try {
            // TODO: authenticate and redirect user to main page after successful registration
            //      move "try catch" to service
            User registered = userService.registerNewAccount(regDto);
            log.info("Account {} registered successfully", registered.getEmail());
        } catch (UserAlreadyExistException e) {
            bindingResult.rejectValue("email", "user.email",
                    "An account for this email already exists");
            return "registration";
        }
        return "redirect:/";
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}
