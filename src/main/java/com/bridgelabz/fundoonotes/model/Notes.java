package com.bridgelabz.fundoonotes.model;

import java.time.LocalDateTime;
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
    private boolean archived;
   

	@Column(columnDefinition="boolean default false")
    private boolean trashed;
	
    @Column(columnDefinition="boolean default false")
    private boolean pinned;
    
    @Column()
    private LocalDateTime reminder;
    
    private String color;
    
    @Column()
    private LocalDateTime lastupdate=LocalDateTime.now();
   
   @Column()
   private LocalDateTime CreatedTime=LocalDateTime.now();
   
   @JsonIgnore
   @ManyToOne
   private UserInfo userdetails;
   
   
   @ManyToMany
   private List<Labels> labels;
   
   
   
   @OneToMany(mappedBy="notes")
   private List<Collaborator> collaborator;
   



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





public LocalDateTime getReminder() {
	return reminder;
}


public void setReminder(LocalDateTime reminder) {
	this.reminder = reminder;
}


public List<Labels> getLabels() {
	return labels;
}

public void setLabels(List<Labels> labels) {
	this.labels = labels;
}

public List<Collaborator> getCollaborator() {
	return collaborator;
}


public void setCollaborator(List<Collaborator> collaborator) {
	this.collaborator = collaborator;
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


public boolean isArchived() {
	return archived;
}


public void setArchived(boolean archived) {
	this.archived = archived;
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


public String getColor() {
	return color;
}


public void setColor(String color) {
	this.color = color;
}





public LocalDateTime getLastupdate() {
	return lastupdate;
}


public void setLastupdate(LocalDateTime lastupdate) {
	this.lastupdate = lastupdate;
}







public LocalDateTime getCreatedTime() {
	return CreatedTime;
}


public void setCreatedTime(LocalDateTime createdTime) {
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
	return "Notes [id=" + id + ", title=" + title + ", takeanote=" + takeanote + ", archived=" + archived + ", trashed="
			+ trashed + ", pinned=" + pinned + ", reminder=" + reminder + ", color=" + color + ", lastupdate="
			+ lastupdate + ", CreatedTime=" + CreatedTime + ", userdetails=" + userdetails + ", labels=" + labels
			+ ", collaborator=" + collaborator + "]";
}




}
