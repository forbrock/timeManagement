package com.spring.project.controller;

import com.spring.project.dto.RegistrationDto;
import com.spring.project.exceptions.UserAlreadyExistException;
import com.spring.project.model.User;
import com.spring.project.model.enums.Role;
import com.spring.project.model.enums.State;
import com.spring.project.service.ActivityService;
import com.spring.project.service.CategoryService;
import com.spring.project.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private CategoryService categoryService;
    private ActivityService activityService;

    @Autowired
    public AdminController(UserService userService, CategoryService categoryService,
                           ActivityService activityService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.activityService = activityService;
    }

    @GetMapping("/users")
    public String showAllUsers(@ModelAttribute("user") RegistrationDto regDto, Model model,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users";
        }
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping
    public String showAdminPage() {
        return "admin";
    }

    @GetMapping("/categories")
    public String showCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories";
    }

    @GetMapping("/activities")
    public String showActivities(Model model) {
        model.addAttribute("activities", activityService.getAllActivities());
        return "activities";
    }

    @PostMapping("/create")
    public String createNewUser(@ModelAttribute("user")
                                    @Valid RegistrationDto regDto, Model model) {
        // TODO: move "try-catch" to the service
        try {
            User created = userService.registerNewAccount(regDto);
        } catch (UserAlreadyExistException e) {
            log.error("User with such email ({}) already exist", regDto.getEmail());
            model.addAttribute("error_message",
                    "An account for this email already exists");
            return "users";
        }
        return "redirect:/users";
    }
}
