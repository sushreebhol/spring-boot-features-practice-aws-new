package com.test.practice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "good")
@PropertySource("classpath:messages.properties")
public class InternationalizationProps {

    private String morning;
}


