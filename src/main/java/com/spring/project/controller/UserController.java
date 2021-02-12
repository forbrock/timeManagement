package com.spring.project.controller;

import com.spring.project.service.ActivityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/")
public class UserController {
    private ActivityService activityService;

    @Autowired
    public UserController(ActivityService activityService) {
        this.activityService = activityService;
    }

/*
    @GetMapping
    public String showUserPage(Model model) {
        model.addAttribute("activities", activityService.getCurrentUserActivities());
        return "index";
    }
*/
/*
    // TODO: after test replace by method above
    @GetMapping
    @ResponseBody
    public Set<UserActivity> showUserPage() {
        return activityService.getCurrentUserActivities();
    }
*/
}
