package com.test.practice.controller;

import com.test.practice.model.Person;
import com.test.practice.service.PersonSerivce;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("public")
public class PublicController {

    @Autowired
    private PersonSerivce personService;

    //@RequestMapping(value = "register", method = GET)
    @GetMapping("/register")
    public String displayRegisterPage(Model model){
        model.addAttribute("person", new Person());
        return "register.html";
    }

    @PostMapping("/createUser")
    public String createUser(@Valid @ModelAttribute("person") Person person, Errors errors){
        if(errors.hasErrors()){
            return "register.html";
        }
        boolean isSaved = personService.createNewPerson(person);
        if(isSaved) {
            return "redirect:/login?register=true";
        }else{
            return "register.html";
        }
    }
}
