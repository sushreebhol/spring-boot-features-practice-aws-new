package com.test.practice.rest;

import com.test.practice.constants.Constants;
import com.test.practice.model.Contact;
import com.test.practice.model.Response;
import com.test.practice.repository.ContactRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/contact", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:8080") - It will allow the specific domain to consume API
//@CrossOrigin - when a web app UI deployed on a server is trying to communicate with a REST service deployed on another server
// then these kind of communications we can allow with the help of @CrossOrigin annotation.
//@CrossOrigin allows clients from any domain to consume the API
public class ContactRestController {
    @Autowired
    ContactRepository contactRepository;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/getMessagesByStatus")
    public List<Contact> getMessagesByStatus(@RequestParam(name = "status")  String status){
        List<Contact> contactList = contactRepository.findByStatus(status);
        return contactList;
    }

   /* @GetMapping("/getMessagesByStatus")
    public EntityModel<Contact> getMessagesByStatus(@RequestParam(name = "status")  String status){
        List<Contact> contactList = contactRepository.findByStatus(status);
        EntityModel<Contact> resource = null;
        for (final Contact contact : contactList) {
            resource = EntityModel.of(contact);
            WebMvcLinkBuilder linkTo = 	linkTo(methodOn(this.getClass()).getAllMessages());
            resource.add(linkTo.withRel("all-messages"));
        }
        return resource;
    }*/

    @GetMapping("/getAllMessagesByStatus")
    public List<Contact> getAllMessagesByStatus(@RequestBody Contact contact){
        if(null != contact && null != contact.getStatus()){
            return contactRepository.findByStatus(contact.getStatus());
        }else{
            return List.of();
        }
       //Example of sending response by explicitly setting in ResponseEntity
       // return ResponseEntity.status(HttpStatus.OK).body(contactRepository.findByStatus(contact.getStatus()));
    }

    @PostMapping("/saveMessage")
    public ResponseEntity<Response> saveMessage(@RequestHeader("invocationFrom") String invocationFrom,
                                            @Valid @RequestBody Contact contact){
        //BindingResult bindingResult
        // if (bindingResult.hasErrors()) {
        // Validation failed, redirect back to form with errors
        //            return "register";
        //        }
        log.info(String.format("Header invocationFrom = %s", invocationFrom));
        contactRepository.save(contact);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message saved successfully");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSaved", "true")
                .body(response);
    }

   @DeleteMapping("/deleteMessage")
    public ResponseEntity<Response> deleteMessage(RequestEntity<Contact> requestEntity){
        //It will give you all the headers information
        HttpHeaders headers = requestEntity.getHeaders();
        headers.forEach((key, value) -> {
            log.info(String.format(
                    "Header '%s' = %s", key, value.stream().collect(Collectors.joining("|"))));
        });
        Contact contact = requestEntity.getBody();
        contactRepository.deleteById(contact.getContactId());
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message successfully deleted");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/closeMessage")
    public ResponseEntity<Response> closeMessage(@RequestBody Contact contactReq){
        Response response = new Response();
        Optional<Contact> contact = contactRepository.findById(contactReq.getContactId());
        if(contact.isPresent()){
            contact.get().setStatus(Constants.CLOSE);
            contactRepository.save(contact.get());
        }else{
            response.setStatusCode("400");
            response.setStatusMsg("Invalid Contact ID received");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
        response.setStatusCode("200");
        response.setStatusMsg("Message successfully closed");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/getAllMessages")
    public List<Contact> getAllMessages(){
        return contactRepository.findAll();
    }
    @GetMapping(path = "/internationalization")
    public String internationalization() {
        return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale()); //Accept-Language = "NL"
    }

    //Version you can do in 3-ways
    // 1. URI Versioning
    // http://localhost:8080/v1/person
    // http://localhost:8080/v2/person

    // 2. Request parameter versioning
    // http://localhost:8080/person?version=1 (@GetMapping(path = "/person", params = "version=1"))
    // http://localhost:8080/person?version=2 (@GetMapping(path = "/person", params = "version=2"))

    // 3. (Custom) headers versioning
    // http://localhost:8080/person/accept  (@GetMapping(path = "/person/accept", headers = "X-API-VERSION=1"))
    // http://localhost:8080/person/accept  (@GetMapping(path = "/person/accept", headers = "X-API-VERSION=2"))
    // In this case in POSTMAN in Header set Key=X-API-VERSION and value = 1

    //4. Media Type Versioning( This is called content negotiation or accept header)
    // http://localhost:8080/person/accept  (@GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v1+json"))
    // http://localhost:8080/person/accept  (@GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v2+json"))
    // In this case in POSTMAN in Header set key=Accept and value=application/vnd.company.app-v1+json


    //HATEOAS - HATEOAS stands for Hypermedia As The Engine Of Application State.
    //When the browser loads the page, you definitely can see all the content that the page has to offer.
    // More interestingly, the page also allows you to perform a lot of actions around that data,

    //The single most important reason for HATEOAS is loose coupling.
    // If a consumer of a REST service needs to hard-code all the resource URLs,
    // then it is tightly coupled with your service implementation. Instead,
    // if you return the URLs, it could use for the actions, then it is loosely coupled.
    // There is no tight dependency on the URI structure, as it is specified and used from the response.

    //Filtering
    //Static Filtering - @JsonIgnore , @JsonIgnoreProperties
    //Static filtering is used if you don't want a specific property of pojo to be send in the JSON response (Rest api response)

    //Dynamic Filtering - @JsonFilter("filtername") - In this parameter filtername is the reference which should match
    //with the reference provided in the controller for specific rest API
    //Dynamic Filtering is used if you don't want a specific property of pojo to be send in the JSON response for specific REST API


    //Actuator - Spring boot actuator provides production ready features
    //Monitoring and managing application in production
    //It provides number of end points
    // 1. beans - Complete list of spring beans in your app
    // 2. health - Application health information
    // 3. metrics - application metrics
    // 4. mappings - details around request mappings

}