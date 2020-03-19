 package com.todoapplication.app.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todoapplication.app.model.RoleName;

@Entity(name = "ROLES")
public class Roles {
	
	    @Id
	    @Column(name = "ROLE_ID")
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	    @SequenceGenerator(name = "user_seq", allocationSize = 1)
	    private Long id;

	    @Column(name = "ROLE_NAME")
	    @Enumerated(EnumType.STRING)
	    private RoleName role;

	    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	    @JsonIgnore
	    private Set<Users> userList = new HashSet<>();

	    public Roles(RoleName role) {
	        this.role = role;
	    }

	    public Roles() {

	    }

	    public boolean isAdminRole() {
	        return null != this && this.role.equals(RoleName.ROLE_ADMIN);
	    }

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public RoleName getRole() {
	        return role;
	    }

	    public void setRole(RoleName role) {
	        this.role = role;
	    }

	    public Set<Users> getUserList() {
	        return userList;
	    }

	    public void setUserList(Set<Users> userList) {
	        this.userList = userList;
	    }
}
