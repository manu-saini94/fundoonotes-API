package com.bridgelabz.fundoonotes.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Labels {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	private String labelname;

	@JsonIgnore
	@ManyToOne
	private UserInfo userdetails;

	@JsonIgnore
	@ManyToMany(mappedBy = "labels")
	private List<Notes> notes;

	public Labels() {
		super();
	}

	public Labels(String labelname, UserInfo userdetails) {
		super();
		this.labelname = labelname;
		this.userdetails = userdetails;
	}

	public UserInfo getUserdetails() {
		return userdetails;
	}

	public void setUserdetails(UserInfo userdetails) {
		this.userdetails = userdetails;
	}

	public List<Notes> getNotes() {
		return notes;
	}

	public void setNotes(List<Notes> notes) {
		this.notes = notes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabelname() {
		return labelname;
	}

	public void setLabelname(String labelname) {
		this.labelname = labelname;
	}

	public String toString() {
		return "Label [id=" + id + ", labelname=" + labelname + "]";
	}

}
