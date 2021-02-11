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
import com.spring.project.model.User;
import com.spring.project.model.UserActivity;
import com.spring.project.service.ActivityService;
import com.spring.project.service.CategoryService;
import com.spring.project.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Set;

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

    @GetMapping
    public String showAdminPage() {
        return "admin";
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

    @PostMapping("/users")
    public String createNewUser(@ModelAttribute("user") @Valid RegistrationDto regDto,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "users";
        }

        try {
            userService.registerNewAccount(regDto);
        } catch (UserAlreadyExistException e) {
            log.error("User with such email ({}) already exist", regDto.getEmail());
            model.addAttribute("error_message",
                    "An account for this email already exists");
            return "users";
        }
        return "redirect:/admin/users";
    }

    // TODO: add possibility to change user role
    @GetMapping("/user/edit/{id}")
    public String showEditUserForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "edit_user";
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
        return "categories";
    }

    @PostMapping("/categories")
    public String createNewCategory(@ModelAttribute("category") CategoryDto categoryDto,Model model) {

        try {
            Category created = categoryService.create(categoryDto);
        } catch (CategoryAlreadyExistException e) {
            log.error("Category with such name ({}) already exist", categoryDto.getName());
            model.addAttribute("error_message",
                    "Such category already exists");
            return "categories";
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("/category/edit/{id}")
    public String showEditCategoryForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("category", categoryService.getById(id));
        return "edit_category";
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
    public String showActivities(@ModelAttribute("activity") ActivityDto activityDto, Model model) {
        model.addAttribute("activities", activityService.getAll());
        model.addAttribute("categories", categoryService.getAll());
        return "activities";
    }

    @PostMapping("/activities")
    public String createNewActivity(@ModelAttribute("activity") ActivityDto activityDto,
                                    @RequestParam("category") String category, Model model) {

        try {
            Activity created = activityService.create(activityDto);
        } catch (ActivityAlreadyExistException e) {
            log.error("Activity with such name ({}) already exist", activityDto.getName());
            model.addAttribute("error_message",
                    "Such activity already exists");
            return "activities";
        }
        return "redirect:/admin/activities";
    }

    @GetMapping("/activity/edit/{id}")
    public String showEditActivityForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("activity", activityService.getById(id));
        model.addAttribute("categories", categoryService.getAll());
        return "edit_activity";
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

    @GetMapping("/test")
    public void throwException() {
        throw new RuntimeException("TEST EXCEPTION");
    }

    @RequestMapping(value="/test2", method = RequestMethod.GET,  headers="Accept=*/*")
    public @ResponseBody ModelAndView oneFaultyMethod() {
        if(true) {
            throw new NullPointerException("This error message if for demo only.");
        }
        return null;
    }

    @GetMapping("/user/{id}/activities")
    public String showUserActivities(@RequestParam("id") long id, Model model) {
        final Set<UserActivity> userActivities = userService.getById(id).getActivities();
        return "user_activities";
    }
}
