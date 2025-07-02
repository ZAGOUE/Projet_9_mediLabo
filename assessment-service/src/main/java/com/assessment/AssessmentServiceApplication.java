package com.assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.assessment.proxy")
public class AssessmentServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AssessmentServiceApplication.class, args);
	}
}
