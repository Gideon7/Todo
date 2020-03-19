package com.todoapplication.app.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Registration Request", description = "The registration request payload")
public class UserRegistrationDTO { 
	@NotBlank(message = "Registration email can be null but not blank")
    @ApiModelProperty(value = "A valid email", required = true, allowableValues = "NonEmpty String")
    private String firstName;
   @NotBlank(message = "Registration email can be null but not blank")
   @ApiModelProperty(value = "A valid email", required = true, allowableValues = "NonEmpty String")
   private String email;
   @NotNull(message = "Registration password cannot be null")
   @ApiModelProperty(value = "A valid password string", required = true, allowableValues = "NonEmpty String")
   private String password;
   private String phone;
   private String lastName;
   @NotNull(message = "Specify whether the user has to be registered as an admin or not")
   @ApiModelProperty(value = "Flag denoting whether the user is an admin or not", required = true,
           dataType = "boolean", allowableValues = "true, false")
   private Boolean registerAsAdmin;
   
	public UserRegistrationDTO(String firstName, String email, String password, 
			String phone, String lastName) {
		super();
		this.firstName = firstName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.lastName=lastName;
	}
	public UserRegistrationDTO() {
		
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setUsername(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Boolean getRegisterAsAdmin() {
		return registerAsAdmin;
	}

	public void setRegisterAsAdmin(Boolean registerAsAdmin) {
		this.registerAsAdmin = registerAsAdmin;
	}
	
}
