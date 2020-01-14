package com.bridgelabz.fundoonotes.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class UserInfo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String username;
	private String Firstname;
	
    private String Lastname;
	
    private String email;
	
	@Column(columnDefinition="boolean default false")
    private boolean isemailverified;
    
	private String password;
    
    @Column(columnDefinition="timestamp default current_timestamp")
	private Date createddate;
	
	
	public Date getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
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
	public String getFirstname() {
		return Firstname;
	}
	public void setFirstname(String firstname) {
		Firstname = firstname;
	}
	public String getLastname() {
		return Lastname;
	}
	public void setLastname(String lastname) {
		Lastname = lastname;
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
	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", username=" + username + ", Firstname=" + Firstname + ", Lastname=" + Lastname
				+ ", email=" + email + ", isemailverified=" + isemailverified + ", password=" + password
				+ ", createddate=" + createddate + "]";
	}
	
	
}
