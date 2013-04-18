package com.richmond.riddler;

import org.json.JSONObject;

public class RiddleSequence {

	private String id;
	private String riddletitle;
	private String riddleone;
	private String riddletwo;
	private String riddlethree;
	private String riddleonehint;
	private String riddletwohint;
	private String riddlethreehint;
	private double riddleonelocationLong;
	private double riddleonelocationLat;
	private double riddletwolocationLong;
	private double riddletwolocationLat;
	private double riddlethreelocationLong;
	private double riddlethreelocationLat;
	private double distance;

	public RiddleSequence(String id, String aRiddletitle, String aRiddleone,
			String aRiddletwo, String aRiddlethree, String aRiddleonehint,
			String aRiddletwohint, String aRiddlethreehint,
			double aRiddleonelocationLong, double aRiddleonelocationLat,
			double aRiddletwolocationLong, double aRiddletwolocationLat,
			double aRiddlethreelocationLong, double aRiddlethreelocationLat,
			double aDistance) {
		// TODO Auto-generated constructor stub
		riddletitle = aRiddletitle;
		riddleone = aRiddleone;
		riddletwo = aRiddletwo;
		riddlethree = aRiddlethree;
		riddleonehint = aRiddleonehint;
		riddletwohint = aRiddletwohint;
		riddlethreehint = aRiddlethreehint;
		riddleonelocationLong = aRiddleonelocationLong;
		riddleonelocationLat = aRiddleonelocationLat;
		riddletwolocationLong = aRiddletwolocationLong;
		riddletwolocationLat = aRiddletwolocationLat;
		riddlethreelocationLong = aRiddlethreelocationLong;
		riddlethreelocationLat = aRiddlethreelocationLat;
		distance = aDistance;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRiddletitle() {
		return riddletitle;
	}

	public void setRiddletitle(String riddletitle) {
		this.riddletitle = riddletitle;
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

	public double getRiddleonelocationLong() {
		return riddleonelocationLong;
	}

	public void setRiddleonelocationLong(double riddleonelocationLong) {
		this.riddleonelocationLong = riddleonelocationLong;
	}

	public double getRiddleonelocationLat() {
		return riddleonelocationLat;
	}

	public void setRiddleonelocationLat(double riddleonelocationLat) {
		this.riddleonelocationLat = riddleonelocationLat;
	}

	public double getRiddletwolocationLong() {
		return riddletwolocationLong;
	}

	public void setRiddletwolocationLong(double riddletwolocationLong) {
		this.riddletwolocationLong = riddletwolocationLong;
	}

	public double getRiddletwolocationLat() {
		return riddletwolocationLat;
	}

	public void setRiddletwolocationLat(double riddletwolocationLat) {
		this.riddletwolocationLat = riddletwolocationLat;
	}

	public double getRiddlethreelocationLong() {
		return riddlethreelocationLong;
	}

	public void setRiddlethreelocationLong(double riddlethreelocationLong) {
		this.riddlethreelocationLong = riddlethreelocationLong;
	}

	public double getRiddlethreelocationLat() {
		return riddlethreelocationLat;
	}

	public void setRiddlethreelocationLat(double riddlethreelocationLat) {
		this.riddlethreelocationLat = riddlethreelocationLat;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "RiddleSequence [id=" + id + ", riddletitle=" + riddletitle
				+ ", riddleone=" + riddleone + ", riddletwo=" + riddletwo
				+ ", riddlethree=" + riddlethree + ", riddleonehint="
				+ riddleonehint + ", riddletwohint=" + riddletwohint
				+ ", riddlethreehint=" + riddlethreehint
				+ ", riddleonelocationLong=" + riddleonelocationLong
				+ ", riddleonelocationLat=" + riddleonelocationLat
				+ ", riddletwolocationLong=" + riddletwolocationLong
				+ ", riddletwolocationLat=" + riddletwolocationLat
				+ ", riddlethreelocationLong=" + riddlethreelocationLong
				+ ", riddlethreelocationLat=" + riddlethreelocationLat
				+ ", distance=" + distance + "]";
	}

	public JSONObject toJSON() {
		JSONObject riddlesObj = new JSONObject();
		JSONObject hintsObj = new JSONObject();
		JSONObject locationOne = new JSONObject();
		JSONObject locationTwo = new JSONObject();
		JSONObject locationThree = new JSONObject();
		JSONObject JSONriddle = new JSONObject();

		try {

			riddlesObj.put(Web.RIDDLE_ONE, riddleone);
			riddlesObj.put(Web.RIDDLE_TWO, riddletwo);
			riddlesObj.put(Web.RIDDLE_THREE, riddlethree);

			hintsObj.put(Web.RIDDLE_ONE_HINT, riddleonehint);
			hintsObj.put(Web.RIDDLE_TWO_HINT, riddletwohint);
			hintsObj.put(Web.RIDDLE_THREE_HINT, riddlethreehint);

			locationOne.put(Web.LONG, riddleonelocationLong);
			locationOne.put(Web.LAT, riddleonelocationLat);
			locationTwo.put(Web.LONG, riddletwolocationLong);
			locationTwo.put(Web.LAT, riddletwolocationLat);
			locationThree.put(Web.LONG, riddlethreelocationLong);
			locationThree.put(Web.LAT, riddlethreelocationLat);

			JSONriddle.put(Web.RIDDLE_TITLE, riddletitle);
			JSONriddle.put(Web.RIDDLES, riddlesObj);
			JSONriddle.put(Web.HINTS, hintsObj);
			JSONriddle.put(Web.RIDDLE_ONE_LOCATION, locationOne);
			JSONriddle.put(Web.RIDDLE_TWO_LOCATION, locationTwo);
			JSONriddle.put(Web.RIDDLE_THREE_LOCATION, locationThree);
			JSONriddle.put(Web.DISTANCE, distance);
		} catch (Exception e) {
			e.printStackTrace();
			// createDialog("Error", "Cannot Estabilish Connection");
		}
		
		return JSONriddle;
	}

}
