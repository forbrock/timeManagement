package com.spring.project.controller;

import com.spring.project.dto.LoginDto;
import com.spring.project.exceptions.CredentialsException;
import com.spring.project.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
@RequestMapping("/auth")
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login";
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/login")
    public void loginUser(LoginDto loginDto) throws CredentialsException {
        userService.getUser(loginDto);
    }
}