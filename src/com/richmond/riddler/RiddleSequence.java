package com.richmond.riddler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

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
	private String createdby;

	public RiddleSequence(String aID, String aRiddletitle, String aRiddleone,
			String aRiddletwo, String aRiddlethree, String aRiddleonehint,
			String aRiddletwohint, String aRiddlethreehint,
			double aRiddleonelocationLong, double aRiddleonelocationLat,
			double aRiddletwolocationLong, double aRiddletwolocationLat,
			double aRiddlethreelocationLong, double aRiddlethreelocationLat,
			double aDistance, String aCreatedBy) {
		// TODO Auto-generated constructor stub
		id = aID;
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
		createdby = aCreatedBy;
	}
	
	public RiddleSequence(){
		
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

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
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
				+ ", distance=" + distance + ", createdby=" + createdby + "]";
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
			JSONriddle.put(Web.USERNAME, createdby);
		} catch (Exception e) {
			e.printStackTrace();
			// createDialog("Error", "Cannot Estabilish Connection");
		}

		return JSONriddle;
	}

	public RiddleSequence(JSONObject info) {

		try{

			JSONObject riddles = info.getJSONObject(Web.RIDDLES);
			JSONObject hints = info.getJSONObject(Web.HINTS);
			JSONObject l1 = info.getJSONObject(Web.RIDDLE_ONE_LOCATION);
			JSONObject l2 = info.getJSONObject(Web.RIDDLE_TWO_LOCATION);
			JSONObject l3 = info.getJSONObject(Web.RIDDLE_THREE_LOCATION);

			
			this.id = info.getString("_id");
			this.riddletitle = info.getString(Web.RIDDLE_TITLE);
			this.riddleone = riddles.getString(Web.RIDDLE_ONE);
			this.riddletwo =	riddles.getString(Web.RIDDLE_TWO);
			this.riddlethree = riddles.getString(Web.RIDDLE_THREE);
			this.riddleonehint =	hints.getString(Web.RIDDLE_ONE_HINT);
			this.riddletwohint = hints.getString(Web.RIDDLE_TWO_HINT);
			this.riddlethreehint = hints.getString(Web.RIDDLE_THREE_HINT);
			this.riddleonelocationLong =	l1.getDouble(Web.LONG);
			this.riddleonelocationLat = l1.getDouble(Web.LAT);
			this.riddletwolocationLong = l2.getDouble(Web.LONG);
			this.riddletwolocationLat = l2.getDouble(Web.LAT);
			this.riddlethreelocationLong = l3.getDouble(Web.LONG);
			this.riddlethreelocationLat = l3.getDouble(Web.LAT);
			this.distance = info.getDouble(Web.DISTANCE);
			this.createdby = info.getString(Web.USERNAME);

			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
