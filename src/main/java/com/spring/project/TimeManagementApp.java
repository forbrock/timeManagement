package com.spring.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TimeManagementApp {

	public static void main(String[] args) {
		SpringApplication.run(TimeManagementApp.class, args);
	}

	// TODO: fix logout message handling for registration page,
	//		add labels into modal windows
	//		implement user's possibility to add time for activity
	//		implement admin's possibility to assign a new activity for user
	//		implement pagination
	//		implement filters
}
