package com.test.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.test.practice.repository")
@EntityScan("com.test.practice.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@PropertySource("classpath:messages.properties")
public class SpringBootFeaturesPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFeaturesPracticeApplication.class, args);
	}

}
