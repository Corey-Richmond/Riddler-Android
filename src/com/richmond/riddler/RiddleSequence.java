package com.richmond.riddler;

public class RiddleSequence {
	
	private long id;
	private String riddleone;
	private String riddletwo;
	private String riddlethree;	  
	private String riddleonehint;
	private String riddletwohint;
	private String riddlethreehint;  
	private String riddleonelocation;
	private String riddletwolocation;
	private String riddlethreelocation;
	private String distance;
	  
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRiddleone() {
		return riddleone;
	}
	public void setRiddleone(String riddleone) {
		this.riddleone = riddleone;
	}
	public String getRiddletwo() {
		return riddletwo;
	}
	public void setRiddletwo(String riddletwo) {
		this.riddletwo = riddletwo;
	}
	public String getRiddlethree() {
		return riddlethree;
	}
	public void setRiddlethree(String riddlethree) {
		this.riddlethree = riddlethree;
	}
	public String getRiddleonehint() {
		return riddleonehint;
	}
	public void setRiddleonehint(String riddleonehint) {
		this.riddleonehint = riddleonehint;
	}
	public String getRiddletwohint() {
		return riddletwohint;
	}
	public void setRiddletwohint(String riddletwohint) {
		this.riddletwohint = riddletwohint;
	}
	public String getRiddlethreehint() {
		return riddlethreehint;
	}
	public void setRiddlethreehint(String riddlethreehint) {
		this.riddlethreehint = riddlethreehint;
	}
	public String getRiddleonelocation() {
		return riddleonelocation;
	}
	public void setRiddleonelocation(String riddleonelocation) {
		this.riddleonelocation = riddleonelocation;
	}
	public String getRiddletwolocation() {
		return riddletwolocation;
	}
	public void setRiddletwolocation(String riddletwolocation) {
		this.riddletwolocation = riddletwolocation;
	}
	public String getRiddlethreelocation() {
		return riddlethreelocation;
	}
	public void setRiddlethreelocation(String riddlethreelocation) {
		this.riddlethreelocation = riddlethreelocation;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	@Override
	public String toString() {
		return "RiddleSequence [id=" + id + ", riddleone=" + riddleone
				+ ", riddletwo=" + riddletwo + ", riddlethree=" + riddlethree
				+ ", riddleonehint=" + riddleonehint + ", riddletwohint="
				+ riddletwohint + ", riddlethreehint=" + riddlethreehint
				+ ", riddleonelocation=" + riddleonelocation
				+ ", riddletwolocation=" + riddletwolocation
				+ ", riddlethreelocation=" + riddlethreelocation
				+ ", distance=" + distance + "]";
	} 

	
} 