package com.skillup.skillup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SkillupApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkillupApplication.class, args);
	}

}
