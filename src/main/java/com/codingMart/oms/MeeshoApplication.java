package com.codingMart.oms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MeeshoApplication {
	public static void main(String[] args) {
		SpringApplication.run(MeeshoApplication.class, args);
	}

}
