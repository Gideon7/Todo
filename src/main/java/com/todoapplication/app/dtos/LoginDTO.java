package com.todoapplication.app.dtos;

import javax.validation.constraints.NotBlank;

public class LoginDTO {
	@NotBlank
	private String usernameOrEmail;
	@NotBlank
	private String password;
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsernameOrEmail() {
		return this.usernameOrEmail;
	}
	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}
}
