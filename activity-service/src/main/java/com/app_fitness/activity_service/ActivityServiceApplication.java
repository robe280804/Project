package com.app_fitness.activity_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
		(scanBasePackages = {
		"com.app_fitness.activity_service",
				"com.app_fitness.common_files.validation",
				"com.app_fitness.common_files.config",
				"com.app_fitness.common_files.security.filter"
})
public class ActivityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivityServiceApplication.class, args);
	}

}
