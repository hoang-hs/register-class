package com.example.registerclass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class RegisterClassApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegisterClassApplication.class, args);
    }

}
