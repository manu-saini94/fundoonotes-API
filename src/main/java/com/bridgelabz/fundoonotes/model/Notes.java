package com.bridgelabz.fundoonotes.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
    private boolean archieve;
   

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
   
   public Notes()
   {}
   
public Notes(String title,String takeanote,boolean archieve,boolean trashed,boolean pinned,String reminder,String color)
{
	this.title=title;
	this.takeanote=takeanote;
	this.archieve=archieve;
	this.trashed=trashed;
	this.pinned=pinned;
	this.reminder=reminder;
	this.color=color;
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
public boolean isArchieve() {
	return archieve;
}

public void setArchieve(boolean archieve) {
	this.archieve = archieve;
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

public Date getCreatedTime() {
	return CreatedTime;
}

public void setCreatedTime(Date createdTime) {
	CreatedTime = createdTime;
}

   

}
