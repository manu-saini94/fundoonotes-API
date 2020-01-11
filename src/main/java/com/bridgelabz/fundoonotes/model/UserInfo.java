package com.bridgelabz.fundoonotes.model;

import javax.persistence.Entity;

@Entity
public class UserInfo {

	
	private int id;
	private String username;
	private String Firstname;
	private String Lastname;
	private String email;
	private boolean isemailverified;
	private String password;
	private Date createddate;
	
}
