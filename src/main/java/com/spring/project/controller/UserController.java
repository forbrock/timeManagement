package com.spring.project.controller;

import com.spring.project.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    private ActivityService activityService;

    @Autowired
    public UserController(ActivityService activityService) {
        this.activityService = activityService;
    }

    /*
        @GetMapping
        public String showUserPage(Model model) {
            model.addAttribute("activities", activityService.getActivities());
            return "user_page";
        }
    */

/*
    @GetMapping
    @ResponseBody
    public String showUserPage(Model model) {
        long id = 1;
        model.addAttribute("activities", activityService.getUserActivities(id));
        return "user_page";
    }
*/

}
