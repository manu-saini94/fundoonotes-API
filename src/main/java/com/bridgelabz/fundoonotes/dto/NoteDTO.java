package com.bridgelabz.fundoonotes.dto;


public class NoteDTO {

	private String title;
	private String takeanote;
	private boolean trashed;
	private boolean archieved;
	private boolean pinned;
	private String color;
	private String reminder;
	
	
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
	public String getReminder() {
		return reminder;
	}
	public void setReminder(String reminder) {
		this.reminder = reminder;
	}
	public boolean isTrashed() {
		return trashed;
	}
	public void setTrashed(boolean trashed) {
		this.trashed = trashed;
	}
	
}
