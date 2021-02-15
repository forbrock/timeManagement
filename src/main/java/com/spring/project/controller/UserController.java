package com.spring.project.controller;

import com.spring.project.exceptions.ActivityAlreadyExistException;
import com.spring.project.model.UserActivity;
import com.spring.project.service.ActivityService;
import com.spring.project.service.TimeLogService;
import com.spring.project.service.UserActivityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
@RequestMapping("/")
public class UserController {
    private ActivityService activityService;
    private UserActivityService userActivityService;
    private TimeLogService timeLogService;

    @Autowired
    public UserController(ActivityService activityService,
                          UserActivityService userActivityService,
                          TimeLogService timeLogService) {
        this.activityService = activityService;
        this.userActivityService = userActivityService;
        this.timeLogService = timeLogService;
    }

    @GetMapping
    public String showUserPage(Model model) {
        model.addAttribute("userActivities",
                userActivityService.combineUserActivities(userActivityService.getCurrentUserActivities()));
        model.addAttribute("activities", activityService.getAll());
        return "index";
    }

    @PostMapping
    public String requestNewActivity(@RequestParam("activityId") long id, Model model) {

        try {
            UserActivity ua = userActivityService.requestActivity(id);
        } catch (ActivityAlreadyExistException e) {
            model.addAttribute("error_message", true);
            return "index";
        }
        model.addAttribute("success_message", true);
        return "index";
    }

    @PostMapping("/time/{id}/add")
    public String addActivityTime(@PathVariable("id") long id,
                                  @RequestParam("time") String time) {
        timeLogService.addNewTimePoint(id, time);
        return "redirect:/";
    }

    @GetMapping("/complete/{id}")
    public String completeTask(@PathVariable("id") long id) {
        userActivityService.completeActivity(id);
        return "redirect:/";
    }
}
