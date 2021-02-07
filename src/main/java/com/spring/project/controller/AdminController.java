package com.spring.project.controller;

import com.spring.project.service.ActivityService;
import com.spring.project.service.CategoryService;
import com.spring.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
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
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
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
}
