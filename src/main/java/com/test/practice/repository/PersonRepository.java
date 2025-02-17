package com.test.practice.repository;

import com.test.practice.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Integer> {
    Person readByEmail(String email);
}
