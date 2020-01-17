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
   private boolean isarchieve;
    @Column(columnDefinition="boolean default false")
   private boolean istrash;
    @Column(columnDefinition="boolean default false")
    private boolean ispinned;
   private String reminder;
   private String color;
   @LastModifiedDate
   private Date lastupdate;
   @ManyToMany
   private List<Label> label;
   @OneToMany(mappedBy="notes")
   private List<Images> images;
   @Column(columnDefinition="timestamp default current_timestamp")
   private Date CreatedTime;
   @JsonIgnore
   @OneToOne
   private UserInfo userinfo;
   
   private List<Collaborator> collaborators;

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

public boolean isIsarchieve() {
	return isarchieve;
}

public void setIsarchieve(boolean isarchieve) {
	this.isarchieve = isarchieve;
}

public boolean isIstrash() {
	return istrash;
}

public void setIstrash(boolean istrash) {
	this.istrash = istrash;
}

public boolean isIspinned() {
	return ispinned;
}

public void setIspinned(boolean ispinned) {
	this.ispinned = ispinned;
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

public List<Label> getLabel() {
	return label;
}

public void setLabel(List<Label> label) {
	this.label = label;
}

public List<Images> getImages() {
	return images;
}

public void setImages(List<Images> images) {
	this.images = images;
}

public Date getCreatedTime() {
	return CreatedTime;
}

public void setCreatedTime(Date createdTime) {
	CreatedTime = createdTime;
}

public UserInfo getUserinfo() {
	return userinfo;
}

public void setUserinfo(UserInfo userinfo) {
	this.userinfo = userinfo;
}

public List<Collaborator> getCollaborators() {
	return collaborators;
}

public void setCollaborators(List<Collaborator> collaborators) {
	this.collaborators = collaborators;
}
   
   

}
