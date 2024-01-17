package com.jsrdev.jankenpon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class JankenponApplication {

	public static void main(String[] args) {
		SpringApplication.run(JankenponApplication.class, args);
	}

}
