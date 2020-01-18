package com.bridgelabz.fundoonotes.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

	@NotNull(message=" Username cannot be empty ")
	private String username;
	@NotNull(message=" First name cannot be empty")
	private String firstname;
	@NotNull(message=" Last name cannot be empty")
	private String lastname;
	@NotNull(message=" email cannot be empty")
	//@Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]{2,4}$",message = "give a valid email")
	private String email;
	@NotNull
	private boolean isemailverified;
	@NotNull(message="password cannot be empty")
	@Size(min=8,max=20)
	private String password;
	@NotNull(message="confirmation is necessary")
	private String passwordagain;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isIsemailverified() {
		return isemailverified;
	}
	public void setIsemailverified(boolean isemailverified) {
		this.isemailverified = isemailverified;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordagain() {
		return passwordagain;
	}
	public void setPasswordagain(String passwordagain) {
		this.passwordagain = passwordagain;
	}
}
