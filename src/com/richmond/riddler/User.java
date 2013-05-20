package com.richmond.riddler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class User implements Serializable {


	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", userName=" + userName
				+ ", points=" + points + ", riddlesCreated=" + riddlesCreated
				+ ", riddlesStarted=" + riddlesStarted
				+ ", riddlesSolvedWithSkip=" + riddlesSolvedWithSkip
				+ ", riddlesSolvedWithoutSkip=" + riddlesSolvedWithoutSkip
				+ "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 0234;
	private String firstName;
	private String userName;
	private int points;
	private List<String> riddlesCreated;
	private List<RiddlesStarted> riddlesStarted;
	private List<String> riddlesSolvedWithSkip;
	private List<String> riddlesSolvedWithoutSkip;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public List<String> getRiddlesCreated() {
		return riddlesCreated;
	}

	public void setRiddlesCreated(List<String> riddlesCreated) {
		this.riddlesCreated = riddlesCreated;
	}

	public void setRiddlesCreatedFromJSON(JSONArray info) {
		this.riddlesCreated = new ArrayList<String>();
		for (int i = 0; i < info.length(); ++i) {
			try {
				this.riddlesCreated.add(info.getString(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<RiddlesStarted> getRiddlesStarted() {
		return riddlesStarted;
	}

	public void setRiddlesStarted(List<RiddlesStarted> riddlesStarted) {
		this.riddlesStarted = riddlesStarted;
	}

	public void setRiddlesStartedFromJSON(JSONArray info) {
		this.riddlesStarted = new ArrayList<RiddlesStarted>();
		for (int i = 0; i < info.length(); ++i) {
			try {
				JSONObject data = new JSONObject();
				data = info.getJSONObject(i);
				RiddlesStarted started = new RiddlesStarted(
						data.getString(Web.ID), data.getInt(Web.CURRENTRIDDLE),
						data.getBoolean(Web.HINTS), data.getBoolean(Web.SKIPS), data.getBoolean(Web.FINISHEDWITHSKIP), 
						data.getBoolean(Web.FINISHEDWITHOUTSKIP));
				this.riddlesStarted.add(started);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<String> getRiddlesSolvedWithSkip() {
		return riddlesSolvedWithSkip;
	}

	public void setRiddlesSolvedWithSkip(List<String> riddlesSolvedWithSkip) {
		this.riddlesSolvedWithSkip = riddlesSolvedWithSkip;
	}

	public void setRiddlesSolvedWithSkipFromJSON(JSONArray info) {
		this.riddlesSolvedWithSkip = new ArrayList<String>();
		for (int i = 0; i < info.length(); ++i) {
			try {
				this.riddlesSolvedWithSkip.add(info.getString(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public List<String> getRiddlesSolvedWithoutSkip() {
		return riddlesSolvedWithoutSkip;
	}

	public void setRiddlesSolvedWithoutSkip(
			List<String> riddlesSolvedWithoutSkip) {
		this.riddlesSolvedWithoutSkip = riddlesSolvedWithoutSkip;
	}

	public void setRiddlesSolvedWithoutSkipCreatedFromJSON(JSONArray info) {
		this.riddlesSolvedWithoutSkip = new ArrayList<String>();
		for (int i = 0; i < info.length(); ++i) {
			try {
				this.riddlesSolvedWithoutSkip.add(info.getString(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void saveUser() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(new File("/sdcard/save_object.txt"))); // Select
																				// where
																				// you
																				// wish
																				// to
																				// save
																				// the
																				// file...
			oos.writeObject(this); // write the class as an 'object'
			oos.flush(); // flush the stream to insure all of the information
							// was written to 'save_object.bin'
			oos.close();// close the stream
		} catch (Exception ex) {
			Log.v("Serialization Save Error : ", ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void loadSerializedObject() {
		File f = new File("/sdcard/save_object.txt");
		try {
			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream(f));
			Object o = ois.readObject();
			copy((User) o);
		} catch (Exception ex) {
			Log.v("Serialization Read Error : ", ex.getMessage());
			ex.printStackTrace();
		}
		//copy(null);
	}

	public void copy(User x) {
		this.firstName = x.firstName;
		this.points = x.points;
		this.riddlesCreated = x.riddlesCreated;
		this.riddlesSolvedWithoutSkip = x.riddlesSolvedWithoutSkip;
		this.riddlesSolvedWithSkip = x.riddlesSolvedWithSkip;
		this.riddlesStarted = x.riddlesStarted;
		this.userName = x.userName;
	}

}

