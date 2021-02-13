package com.spring.project.controller;

import com.spring.project.dto.ActivityDto;
import com.spring.project.dto.UserActivityDto;
import com.spring.project.dto.mapper.ActivityMapper;
import com.spring.project.exceptions.ActivityAlreadyExistException;
import com.spring.project.model.Activity;
import com.spring.project.model.UserActivity;
import com.spring.project.service.ActivityService;
import com.spring.project.service.UserActivityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/")
public class UserController {
    private ActivityService activityService;
    private UserActivityService userActivityService;
    private ActivityMapper mapper;

    @Autowired
    public UserController(ActivityService activityService,
                          ActivityMapper mapper,
                          UserActivityService userActivityService) {
        this.activityService = activityService;
        this.mapper = mapper;
        this.userActivityService = userActivityService;
    }

    @GetMapping
    public String showUserPage(Model model) {
        model.addAttribute("userActivities", activityService.getCurrentUserActivities());
        model.addAttribute("activities", activityService.getAll());
        return "index";
    }

    // TODO: show an error message when trying to add a duplicate entry
    @PostMapping("/user/request")
    public String requestNewActivity(@RequestParam("activityId") long id, Model model) {

        try {
            UserActivity ua = userActivityService.requestActivity(id);
        } catch (ActivityAlreadyExistException e) {
            model.addAttribute("error_message",
                    "valid.user.request.activity.duplicate");
        }
        return "redirect:/";
    }
}
