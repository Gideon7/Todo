package com.todoapplication.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todoapplication.app.entities.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer>{

}
