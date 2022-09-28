package ru.simple.shop.task.testapp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
public class TestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestAppApplication.class, args);
	}

	// Для работы со временем
	@Bean
	public Clock clock() {
		return Clock.systemDefaultZone();
	}

}


