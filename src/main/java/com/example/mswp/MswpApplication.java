package com.example.mswp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class MswpApplication {

    public static void main(String[] args) {
        SpringApplication.run(MswpApplication.class, args);
    }
}
