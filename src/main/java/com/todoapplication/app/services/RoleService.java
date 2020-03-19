package com.todoapplication.app.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoapplication.app.entities.Roles;
import com.todoapplication.app.repositories.RoleRepository;

@Service
public class RoleService {
	
	private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Find all roles from the database
     */
    public Collection<Roles> findAll() {
        return roleRepository.findAll();
    }
}
