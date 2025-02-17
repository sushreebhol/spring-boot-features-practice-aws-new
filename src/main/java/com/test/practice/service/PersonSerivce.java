package com.test.practice.service;

import com.test.practice.constants.Constants;
import com.test.practice.model.Person;
import com.test.practice.model.Roles;
import com.test.practice.repository.PersonRepository;
import com.test.practice.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonSerivce {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createNewPerson(Person person){
        boolean isSaved = false;
        Roles roles = rolesRepository.findByRoleName(Constants.STUDENT_ROLE);
        person.setRoles(roles);
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        person = personRepository.save(person);
        if(person != null && person.getPersonId() > 0){
            isSaved = true;
        }
        return isSaved;
    }
}
