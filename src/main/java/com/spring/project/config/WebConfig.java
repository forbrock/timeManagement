package com.spring.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("public/css/**", "public/img/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/").setViewName("user_page");
        registry.addViewController("/user_page").setViewName("user_page");
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/categories").setViewName("categories");
        registry.addViewController("/activities").setViewName("activities");
        registry.addViewController("/users").setViewName("users");
    }
}
