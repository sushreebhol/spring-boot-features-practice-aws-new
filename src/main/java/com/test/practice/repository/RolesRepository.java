package com.test.practice.repository;

import com.test.practice.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Integer> {
    public Roles findByRoleName(String roleName);
}
