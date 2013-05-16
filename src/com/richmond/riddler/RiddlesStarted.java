package com.richmond.riddler;

import java.io.Serializable;
import java.util.List;

public class RiddlesStarted implements Serializable{
	@Override
	public String toString() {
		return "RiddlesStarted [id=" + id + ", currentRiddle=" + currentRiddle
				+ ", hint=" + hint + ", skip=" + skip + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private int currentRiddle;
	private boolean hint;
	private boolean skip;
	
	
	public RiddlesStarted(String id, int currentRiddle, boolean hint,
			boolean skip) {
		super();
		this.id = id;
		this.currentRiddle = currentRiddle;
		this.hint = hint;
		this.skip = skip;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCurrentRiddle() {
		return currentRiddle;
	}
	public void setCurrentRiddle(int currentRiddle) {
		this.currentRiddle = currentRiddle;
	}
	public boolean isHint() {
		return hint;
	}
	public void setHint(boolean hint) {
		this.hint = hint;
	}
	public boolean isSkip() {
		return skip;
	}
	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public int isIn(List<RiddlesStarted> started){
		
		for(int i = 0; i< started.size(); ++i){
			if(this.id.equals(started.get(i).id)){
				return i;
			}
		}
		return -1;
	}

	
}
