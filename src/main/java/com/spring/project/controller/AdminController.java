package com.spring.project.controller;

import com.spring.project.dto.ActivityDto;
import com.spring.project.dto.CategoryDto;
import com.spring.project.dto.RegistrationDto;
import com.spring.project.dto.UpdateUserDto;
import com.spring.project.exceptions.ActivityAlreadyExistException;
import com.spring.project.exceptions.CategoryAlreadyExistException;
import com.spring.project.exceptions.UserAlreadyExistException;
import com.spring.project.model.Activity;
import com.spring.project.model.Category;
import com.spring.project.model.UserActivity;
import com.spring.project.service.ActivityService;
import com.spring.project.service.CategoryService;
import com.spring.project.service.UserActivityService;
import com.spring.project.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private CategoryService categoryService;
    private ActivityService activityService;
    private UserActivityService userActivityService;

    @Autowired
    public AdminController(UserService userService,
                           CategoryService categoryService,
                           ActivityService activityService,
                           UserActivityService userActivityService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.activityService = activityService;
        this.userActivityService = userActivityService;
    }

    @GetMapping
    public String showAdminPage(Model model) {
        model.addAttribute("userActivities",
                userActivityService.getRequestedActivities());
        return "admin";
    }

    @GetMapping("/users")
    public String showAllUsers(@ModelAttribute("user") RegistrationDto regDto, Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin_users";
    }

    @PostMapping("/users")
    public String createNewUser(@ModelAttribute("user") @Valid RegistrationDto regDto,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin_users";
        }

        try {
            userService.registerNewAccount(regDto);
        } catch (UserAlreadyExistException e) {
            log.error("User with such email ({}) already exist", regDto.getEmail());
            model.addAttribute("error_message",
                    "reg.login.not.unique");
            return "admin_users";
        }
        return "redirect:/admin/admin_users";
    }

    @GetMapping("/user/edit/{id}")
    public String showEditUserForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "admin_edit_user";
    }

    @PostMapping("/user/edit/{id}")
    public String editUser(@PathVariable("id") long id, UpdateUserDto userDto,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/edit/{id}";
        }
        userService.update(userDto);
        return "redirect:/admin/users";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/categories")
    public String showCategories(@ModelAttribute("category") CategoryDto categoryDto, Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "admin_categories";
    }

    @PostMapping("/categories")
    public String createNewCategory(@ModelAttribute("category") CategoryDto categoryDto,Model model) {

        try {
            Category created = categoryService.create(categoryDto);
        } catch (CategoryAlreadyExistException e) {
            log.error("Category with such name ({}) already exist", categoryDto.getName());
            model.addAttribute("error_message",
                    "Such category already exists");
            return "admin_categories";
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("/category/edit/{id}")
    public String showEditCategoryForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("category", categoryService.getById(id));
        return "admin_edit_category";
    }

    @PostMapping("/category/edit/{id}")
    public String editCategory(@PathVariable("id") long id, CategoryDto categoryDto,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "category/edit/{id}";
        }
        categoryService.update(categoryDto);
        return "redirect:/admin/categories";
    }

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") long id) {
        categoryService.deleteById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/activities")
    public String showActivities(Model model) {
        model.addAttribute("activities", activityService.getAll());
        model.addAttribute("categories", categoryService.getAll());
        return "admin_activities";
    }

    @PostMapping("/activities")
    public String createNewActivity(@ModelAttribute("activity") ActivityDto activityDto, Model model) {
        try {
            Activity created = activityService.create(activityDto);
        } catch (ActivityAlreadyExistException e) {
            log.error("Activity with such name ({}) already exist", activityDto.getName());
            model.addAttribute("error_message",
                    "activity.already.exist");
            return "admin_activities";
        } catch (SQLException e) {
            log.error("Trying to create new activity failure, name: {}", activityDto.getName());
        }
        return "redirect:/admin/activities";
    }

    @GetMapping("/activity/edit/{id}")
    public String showEditActivityForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("activity", activityService.getById(id));
        model.addAttribute("categories", categoryService.getAll());
        return "admin_edit_activity";
    }

    @PostMapping("/activity/edit/{id}")
    public String editActivity(@PathVariable("id") long id, ActivityDto activityDto,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "activity/edit/{id}";
        }
        activityService.update(activityDto);
        return "redirect:/admin/activities";
    }

    @GetMapping("/activity/delete/{id}")
    public String deleteActivity(@PathVariable("id") long id) {
        activityService.deleteById(id);
        return "redirect:/admin/activities";
    }

    @GetMapping("/user/{id}/activities")
    public String showUserActivities(@PathVariable("id") long id, Model model) {
        model.addAttribute("userActivities", userActivityService.getUserActivitiesById(id));
        return "admin_user_activities";
    }

    @GetMapping("/user/activity/delete/{id}")
    public String deleteUserActivity(@PathVariable("id") long id) {
        userActivityService.deleteById(id);
        return "admin_user_activities";
    }

    @GetMapping("/request/confirm/{id}")
    public String confirmUserRequest(@PathVariable("id") long id) {
        userActivityService.confirmRequest(id);
        return "redirect:/admin";
    }

    // TODO: inform user if his request has been deleted
    @GetMapping("/request/delete/{id}")
    public String deleteUserRequest(@PathVariable("id") long id) {
        userActivityService.deleteRequest(id);
        return "redirect:/admin";
    }

    @GetMapping("/report")
    public String showReports(Model model) {
        return findPaginated(1, model);
    }

    @GetMapping("/report/{pageNo}")
    public String findPaginated(@PathVariable("pageNo") Integer pageNo, Model model) {
        int pageSize = 5;
        Page<UserActivity> page = userActivityService.findAllPaginated(pageNo, pageSize);
        List<UserActivity> activities = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("allActivities",
                userActivityService.combineUserActivities(activities));
        return "admin_report";
    }
}
