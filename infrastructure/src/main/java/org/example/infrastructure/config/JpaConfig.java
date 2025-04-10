package org.example.infrastructure.config;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "org.example.infrastructure.repositories")
public class JpaConfig {
}