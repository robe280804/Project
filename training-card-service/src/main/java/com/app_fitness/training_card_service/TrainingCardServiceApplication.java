package com.app_fitness.training_card_service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
		(scanBasePackages = {
				"com.app_fitness.training_card_service",
				"com.app_fitness.common_files"
		})
public class TrainingCardServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingCardServiceApplication.class, args);
	}

}
