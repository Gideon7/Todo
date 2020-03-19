package com.todoapplication.app.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.*;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.todoapplication.app.configs.DateAudit;

@Entity(name = "USER")
public class Users extends DateAudit{
	    @Id
	    @Column(name = "USER_ID")
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	    @SequenceGenerator(name = "user_seq", allocationSize = 1)
	    private int id;

	    @Column(name = "EMAIL", unique = true)
	    @NotBlank(message = "User email cannot be null")
	    private String email;

	    @Column(name = "USERNAME", unique = true)
	    @NotBlank(message = "Username can not be blank")
	    private String username;

	    @Column(name = "PASSWORD")
	    @NotNull(message = "Password cannot be null")
	    private String password;

	    @Column(name = "FIRST_NAME")
	    @NotBlank(message = "First name can not be blank")
	    private String firstName;

	    @Column(name = "LAST_NAME")
	    @NotBlank(message = "Last name can not be blank")
	    private String lastName;
	    
	    @Column(name = "PHONE")
	    @NotBlank(message = "Last name can not be blank")
	    private String phone;

	    @Column(name = "IS_ACTIVE", nullable = false)
	    private Boolean active;

	    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	    @JoinTable(name = "USER_AUTHORITY", joinColumns = {
	    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")}, inverseJoinColumns = {
	    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")})
	    private Set<Roles> roles = new HashSet<>();

	    @Column(name = "IS_EMAIL_VERIFIED", nullable = false)
	    private Boolean isEmailVerified;
	    
	    public Users() {
	        super();
	    }

	    public Users(Users user) {
	        id = user.getId();
	        username = user.getUsername();
	        password = user.getPassword();
	        firstName = user.getFirstName();
	        lastName = user.getLastName();
	        email = user.getEmail();
	        active = user.getActive();
	        roles = user.getRoles();
	        isEmailVerified = user.getEmailVerified();
	        phone=user.getPhone();
	        
	    }

	    public void addRole(Roles role) {
	        roles.add(role);
	        role.getUserList().add(this);
	    }

	    public void addRoles(Set<Roles> roles) {
	        roles.forEach(this::addRole);
	    }

	    public void removeRole(Roles role) {
	        roles.remove(role);
	        role.getUserList().remove(this);
	    }

	    public void markVerificationConfirmed() {
	        setEmailVerified(true);
	    }

	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public String getFirstName() {
	        return firstName;
	    }

	    public void setFirstName(String firstName) {
	        this.firstName = firstName;
	    }

	    public String getLastName() {
	        return lastName;
	    }

	    public void setLastName(String lastName) {
	        this.lastName = lastName;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getPhone() {
			return this.phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public Boolean getActive() {
	        return active;
	    }

	    public void setActive(Boolean active) {
	        this.active = active;
	    }

	    public Set<Roles> getRoles() {
	        return roles;
	    }

	    public void setRoles(Set<Roles> authorities) {
	        roles = authorities;
	    }

	    public Boolean getEmailVerified() {
	        return isEmailVerified;
	    }

	    public void setEmailVerified(Boolean emailVerified) {
	        isEmailVerified = emailVerified;
	    }

		@Override
	    public String toString() {
	        return "User{" + "id=" + id + ", email='" + email + '\'' + ", username='" + username + '\'' + ", password='"
	                + password + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", active="
	                + active + ", roles=" + roles + ", isEmailVerified=" + isEmailVerified + ",phone=" + phone + '}';
	    }

		public Object map(Object object) {
			// TODO Auto-generated method stub
			return null;
		}
}
