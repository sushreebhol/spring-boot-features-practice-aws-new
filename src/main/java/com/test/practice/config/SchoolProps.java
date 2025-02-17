package com.test.practice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.Map;

@Component("schoolProps")
@Data
@ConfigurationProperties(prefix = "school") //this referes to the prefix mentioned in application.properties file
@Validated //It indicates to the spring framework to do the validation on the top of the values that are present inside properties file
//based on validation which I have written in this java file like @Min and @Min
//@PropertySource("classpath:messages.properties")
// @PropertySouce annotation is used when we use different properties file except application.properties file
public class SchoolProps {

    @Min(value=5, message="must be between 5 and 25")
    @Max(value=25, message="must be between 5 and 25")
    private int pageSize;
    private Map<String, String> contact;
    private List<String> branches;
}

