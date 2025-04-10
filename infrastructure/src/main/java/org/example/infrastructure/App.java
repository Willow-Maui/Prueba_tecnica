package org.example.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "org.example",exclude = {UserDetailsServiceAutoConfiguration.class})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}