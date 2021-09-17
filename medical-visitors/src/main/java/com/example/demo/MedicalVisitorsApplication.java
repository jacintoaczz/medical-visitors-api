package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@ComponentScan({ "com.example.controllers", "com.example.services" })
@EntityScan("com.example.models")
@EnableJpaRepositories("com.example.repositories")
@SpringBootApplication
public class MedicalVisitorsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalVisitorsApplication.class, args);
	}

}
