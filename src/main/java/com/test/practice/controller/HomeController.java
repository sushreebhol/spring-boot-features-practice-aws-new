package com.test.practice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class HomeController {

    @Value("${good.morning.message}")
    private String goodMorningMessage;

    @RequestMapping(value={"", "/", "home"})
    public String displayHomePage(){
        log.error("Getting values from Messages.properties file : " + goodMorningMessage);
        return "home.html";
    }
}
