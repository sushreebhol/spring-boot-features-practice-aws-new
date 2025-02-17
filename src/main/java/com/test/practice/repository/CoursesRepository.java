package com.test.practice.repository;

import com.test.practice.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Integer> {

    List<Courses> findByOrderByNameDesc();//To get data in descending order

    List<Courses> findByOrderByName();//To get data in ascending order
}

