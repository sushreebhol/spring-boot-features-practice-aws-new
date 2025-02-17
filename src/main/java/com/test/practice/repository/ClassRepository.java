package com.test.practice.repository;

import com.test.practice.model.EazyClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<EazyClass,Integer> {
}
