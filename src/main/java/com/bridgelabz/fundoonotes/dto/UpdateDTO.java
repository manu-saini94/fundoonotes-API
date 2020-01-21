package com.bridgelabz.fundoonotes.dto;

import java.util.List;

public class UpdateDTO {

	private int id;
	private String title;
	private String takeanote;
	private boolean isarchieve;
	private boolean ispinned;
	private boolean istrash;
	private String color; 
	private String reminder;
	private List<String> label;
	private List<String> images;
	
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
	public boolean isIspinned() {
		return ispinned;
	}
	public void setIspinned(boolean ispinned) {
		this.ispinned = ispinned;
	}
	public boolean isIstrash() {
		return istrash;
	}
	public void setIstrash(boolean istrash) {
		this.istrash = istrash;
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
	public List<String> getLabel() {
		return label;
	}
	public void setLabel(List<String> label) {
		this.label = label;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
}
