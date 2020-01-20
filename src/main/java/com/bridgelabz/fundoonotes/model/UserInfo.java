package com.bridgelabz.fundoonotes.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserInfo implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String username;
	private String Firstname;
	
    private String Lastname;
	
    private String email;
    private String password;
	
	@Column(columnDefinition="boolean default false")
    private boolean isEmailVerified;

	@Column(columnDefinition="timestamp default current_timestamp")
	private Date createddate;
	
	@JsonIgnore
	@OneToMany(mappedBy = "userdetails")
	private List<Notes> notes;
	
	@JsonIgnore
	@OneToMany(mappedBy = "userdetails")
	private List<Labels> labels;
	

	public List<Labels> getLabels() {
		return labels;
	}
	public void setLabels(List<Labels> labels) {
		this.labels = labels;
	}
	public List<Notes> getNotes() {
		return notes;
	}
	public void setNotes(List<Notes> notes) {
		this.notes = notes;
	}
	
	public boolean isEmailVerified() {
		return isEmailVerified;
	}
	public void setEmailVerified(boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}
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
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", username=" + username + ", Firstname=" + Firstname + ", Lastname=" + Lastname
				+ ", email=" + email + ",  isEmailVerified=" + isEmailVerified + ",password=" + password
				+ ", createddate=" + createddate + "]";
	}

	
	
}
