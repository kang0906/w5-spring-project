package com.example.sparta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpartaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpartaApplication.class, args);
	}
}

