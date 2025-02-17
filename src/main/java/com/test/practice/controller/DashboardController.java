package com.test.practice.controller;

import com.test.practice.config.SchoolProps;
import com.test.practice.model.Person;
import com.test.practice.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class DashboardController {

    @Autowired
    PersonRepository personRepository;

    //Example of reading data from properties file
    @Autowired
    private SchoolProps eazySchoolProps;

    @Value("${school.pageSize}")
    private int defaultPageSize;

    @Value("${school.contact.pageSize}")
    private String contactPageSize;

    @Value("${school.contact.successMsg}")
    private String message;

    @Value("${good.morning.message}")
    private String goodMorningMessage;

    @Value("${value.from.file}")
    private String valueFromFile;

    @Autowired
    Environment environment;

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session) {
        Person person = personRepository.readByEmail(authentication.getName());
        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        if(null != person.getEazyClass() && null != person.getEazyClass().getName()){
            model.addAttribute("enrolledClass", person.getEazyClass().getName());
        }
        session.setAttribute("loggedInPerson", person);
        logMessages();
        //Example of reading data from properties file
        return "dashboard.html";
    }

    private void logMessages() {
        log.error("Error message from the Dashboard page");
        log.warn("Warning message from the Dashboard page");
        log.info("Info message from the Dashboard page");
        log.debug("Debug message from the Dashboard page");
        log.trace("Trace message from the Dashboard page");
        log.trace("--------------------------------------------");

        log.error("defaultPageSize value with @Value annotation is : "+defaultPageSize);
        log.error("contactPageSize value with @Value annotation is : "+contactPageSize);
        log.error("successMsg value with @Value annotation is : "+message);
        log.trace("---------------------------------------------------------------------------------------------");

        log.error("defaultPageSize value using environment variable is : "+environment.getProperty("school.pageSize"));
        log.error("successMsg value using environment variable is : "+environment.getProperty("school.contact.successMsg"));
        log.error("Java Home using environment variable is : "+environment.getProperty("JAVA_HOME"));
        log.error("school branch using environment variable is : "+environment.getProperty("school.branches[0]"));
        log.trace("---------------------------------------------------------------------------------------------");

        log.error("defaultPageSize value using @ConfigurationProperties annotation is : "+ eazySchoolProps.getContact().get("pageSize"));
        log.error("successMsg value using @ConfigurationProperties annotation is : "+ eazySchoolProps.getContact().get("successMsg"));

        log.error("1st branch value using @ConfigurationProperties annotation is : "+ eazySchoolProps.getBranches().get(0));
        log.error("2nd branch value using @ConfigurationProperties annotation is : "+ eazySchoolProps.getBranches().get(1));

        log.error("Getting values from Messages.properties file : " + goodMorningMessage);
        log.trace("---------------------------------------------------------------------------------------------");
        log.error("Getting values using system properties : " + valueFromFile);
    }
}