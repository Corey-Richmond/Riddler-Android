package com.richmond.riddler;

import com.richmond.riddler.http.HttpPostRiddle;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateRiddleActivity extends Activity implements OnClickListener {

	private AlertDialog mAlertDialog;
	public static final int DIALOG_CREATE_RIDDLE_FAILURE = 1;
	public static final int DIALOG_TITLE_ERROR = 2;
	public static final int DIALOG_RIDDLE_ERROR = 3;
	public static final int DIALOG_HINT_ERROR = 4;
	public static final int DIALOG_LOCATION_ERROR = 5;

	public final static String RIDDLESANDHINTS = "com.richmond.riddler.MESSAGE";
	public final static String DISTANCE = "com.richmond.riddler.DISTANCE";
	public final static String LOCATION = "com.richmond.riddler.LOCATION";

	private Button doneButton, pinRiddleOne, pinRiddleTwo, pinRiddleThree;
	private EditText riddle1, riddle2, riddle3, riddleTitle, riddle1hint,
			riddle2hint, riddle3hint;
	private double lat1, longi1, lat2, longi2, lat3, longi3, distance;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_riddle);

		doneButton = (Button) findViewById(R.id.donebutton);
		pinRiddleOne = (Button) findViewById(R.id.pinfirstriddle);
		pinRiddleTwo = (Button) findViewById(R.id.pinsecondriddle);
		pinRiddleThree = (Button) findViewById(R.id.pinthirdriddle);

		riddle1 = (EditText) findViewById(R.id.riddle1);
		riddle2 = (EditText) findViewById(R.id.riddle2);
		riddle3 = (EditText) findViewById(R.id.riddle3);
		riddle1hint = (EditText) findViewById(R.id.riddle1hint);
		riddle2hint = (EditText) findViewById(R.id.riddle2hint);
		riddle3hint = (EditText) findViewById(R.id.riddle3hint);
		riddleTitle = (EditText) findViewById(R.id.riddletitle);

		doneButton.setOnClickListener(this);
		pinRiddleOne.setOnClickListener(this);
		pinRiddleTwo.setOnClickListener(this);
		pinRiddleThree.setOnClickListener(this);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.donebutton:
			AddRiddleSequence(v);
			break;
		case R.id.pinfirstriddle:
			LaunchMapView(v, 1);
			break;
		case R.id.pinsecondriddle:
			LaunchMapView(v, 2);
			break;
		case R.id.pinthirdriddle:
			LaunchMapView(v, 3);
			break;

		}
	}

	private void LaunchMapView(View v, int riddleNumber) {
		Intent intent = new Intent(this, LocationSelectionActivity.class);
		startActivityForResult(intent, riddleNumber);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {
			if (resultCode == RESULT_OK)
				longi1 = data.getDoubleExtra("resultLongitude", 1);
			lat1 = data.getDoubleExtra("resultLatitude", 1);
			Toast t = Toast.makeText(getBaseContext(), "longi " + longi1
					+ "lat " + lat1, Toast.LENGTH_LONG);
			t.show();
		}
		if (requestCode == 2) {
			if (resultCode == RESULT_OK)
				longi2 = data.getDoubleExtra("resultLongitude", 2);
			lat2 = data.getDoubleExtra("resultLatitude", 2);
			Toast t = Toast.makeText(getBaseContext(), "longi " + longi2
					+ "lat " + lat2, Toast.LENGTH_LONG);
			t.show();
		}
		if (requestCode == 3) {
			if (resultCode == RESULT_OK)
				longi3 = data.getDoubleExtra("resultLongitude", 3);
			lat3 = data.getDoubleExtra("resultLatitude", 3);
			Toast t = Toast.makeText(getBaseContext(), "longi " + longi3
					+ "lat " + lat3, Toast.LENGTH_LONG);
			t.show();
		}

		if (resultCode == RESULT_CANCELED) {

			// Write your code on no result return

		}
	}// onAcrivityResult

	private void AddRiddleSequence(View v) {
		if (validateFields()) {
			
			double[] locations = { lat1,longi1,lat2,longi2,lat3,longi3};
			CalculateTotalDistance(locations);
			RiddleSequence riddles = new RiddleSequence("", riddleTitle.getText()
					.toString(), riddle1.getText().toString(), riddle2
					.getText().toString(), riddle3.getText().toString(),
					riddle1hint.getText().toString(), riddle2hint.getText()
							.toString(), riddle3hint.getText().toString(),
					longi1, lat1, longi2, lat2, longi3, lat3, distance, AbstractGetNameTask.getmEmailAddress());
			
	        HttpPostRiddle post = new HttpPostRiddle(this, riddles);
	        post.execute();
		}
	}
	
	@SuppressWarnings("deprecation")
	private boolean validateFields() {
		if (!validateTitle()) {
			showDialog(DIALOG_TITLE_ERROR);
		} else if (!validateRiddles()) {
			showDialog(DIALOG_RIDDLE_ERROR);
		} else if (!validateHints()) {
			showDialog(DIALOG_HINT_ERROR);
		} else if (!validateLocations()) {
			showDialog(DIALOG_LOCATION_ERROR);
		} else {
			return true;
		}
		return false;
	}

	private boolean validateTitle() {
		return (riddleTitle.getText().toString().length() != 0);
	}

	private boolean validateRiddles() {
		Log.i("riddle1_text", riddle1.getText().toString());
		return (riddle1.getText().toString().length() != 0
				&& riddle2.getText().toString().length() != 0 && riddle3
				.getText().toString().length() != 0);
	}

	private boolean validateHints() {
		return (riddle1hint.getText().toString().length() != 0
				&& riddle2hint.getText().toString().length() != 0 && riddle3hint
				.getText().toString().length() != 0);
	}

	private boolean validateLocations() {
		return (lat1 != 0 && longi1 != 0 && lat2 != 0 && longi2 != 0
				&& lat3 != 0 && longi3 != 0);
	}

	private void CalculateTotalDistance(double[] locations) {
		distance = (DistanceBetweenTwo(locations[0], locations[1],
				locations[2], locations[3]) + DistanceBetweenTwo(locations[2],
				locations[3], locations[4], locations[5]));
	}

	private double DistanceBetweenTwo(double aLat1, double aLong1,
			double aLat2, double aLongi2) {
		double earthRadius = 3958.75;
		double dLat = Math.toRadians(aLat2 - aLat1);
		double dLng = Math.toRadians(aLongi2 - aLong1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(aLat1))
				* Math.cos(Math.toRadians(aLat2)) * Math.sin(dLng / 2)
				* Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		int meterConversion = 1609;

		return (dist * meterConversion) * 0.00062137119; // returns miles

	}

	@SuppressWarnings("deprecation")
	@Override
	public Dialog onCreateDialog(int aId) {
		switch (aId) {
		case DIALOG_TITLE_ERROR:
			return createTryAgainDialog(R.string.dialog_title_error);
		case DIALOG_RIDDLE_ERROR:
			return createTryAgainDialog(R.string.dialog_riddle_error);
		case DIALOG_HINT_ERROR:
			return createTryAgainDialog(R.string.dialog_hint_error);
		case DIALOG_LOCATION_ERROR:
			return createTryAgainDialog(R.string.dialog_location_error);
		}
		return super.onCreateDialog(aId);
	}

	private Dialog createTryAgainDialog(int aMessage) {
		mAlertDialog = new AlertDialog.Builder(this)
				.setMessage(getString(aMessage))
				.setNegativeButton("Try Again", null).setCancelable(true)
				.create();

		return mAlertDialog;
	}

}
