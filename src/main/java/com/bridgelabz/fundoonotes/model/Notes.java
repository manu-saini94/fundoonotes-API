package com.bridgelabz.fundoonotes.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Notes {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String title;
    private String takeanote;
    @Column(columnDefinition="boolean default false")
    private boolean archieved;
   

	@Column(columnDefinition="boolean default false")
    private boolean trashed;
    @Column(columnDefinition="boolean default false")
    private boolean pinned;
    private String reminder;
    private String color;
    @LastModifiedDate
    private Date lastupdate;
   
   @Column(columnDefinition="timestamp default current_timestamp")
   private Date CreatedTime;
   
   @JsonIgnore
   @ManyToOne
   private UserInfo userdetails;
   
   
   @ManyToMany
   private List<Labels> labels;
   

public Notes() {
	super();
}


public Notes(String title, String takeanote, String color, UserInfo userdetails) {
	super();
	this.title = title;
	this.takeanote = takeanote;
	this.color = color;
	this.userdetails = userdetails;
}


public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}


public String getTitle() {
	return title;
}


public void setTitle(String title) {
	this.title = title;
}


public String getTakeanote() {
	return takeanote;
}


public void setTakeanote(String takeanote) {
	this.takeanote = takeanote;
}


public boolean isArchieved() {
	return archieved;
}


public void setArchieved(boolean archieved) {
	this.archieved = archieved;
}


public boolean isTrashed() {
	return trashed;
}


public void setTrashed(boolean trashed) {
	this.trashed = trashed;
}


public boolean isPinned() {
	return pinned;
}


public void setPinned(boolean pinned) {
	this.pinned = pinned;
}


public String getReminder() {
	return reminder;
}


public void setReminder(String reminder) {
	this.reminder = reminder;
}


public String getColor() {
	return color;
}


public void setColor(String color) {
	this.color = color;
}


public Date getLastupdate() {
	return lastupdate;
}


public void setLastupdate(Date lastupdate) {
	this.lastupdate = lastupdate;
}


public Date getCreatedTime() {
	return CreatedTime;
}


public void setCreatedTime(Date createdTime) {
	CreatedTime = createdTime;
}


public UserInfo getUserdetails() {
	return userdetails;
}


public void setUserdetails(UserInfo userdetails) {
	this.userdetails = userdetails;
}


@Override
public String toString() {
	return "Notes [id=" + id + ", title=" + title + ", takeanote=" + takeanote + ", archieved=" + archieved
			+ ", trashed=" + trashed + ", pinned=" + pinned + ", reminder=" + reminder + ", color=" + color
			+ ", CreatedTime=" + CreatedTime + ", userinfo="+userdetails+"]";
}

}
