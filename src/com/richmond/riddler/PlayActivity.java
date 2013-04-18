package com.richmond.riddler;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActivity extends Activity implements OnClickListener {
	
	private long riddleID;
	private GPSTracker gps;
	private int currentRiddle = 1;
	private RiddleSequence riddles;
	private Button hintButton, checkButton, skipButton;
	private boolean hintIsShowing = false, skipWasUsed = false;
	private TextView textViewRiddle, textViewHint, textViewTitle;
	//private RiddlesDataSource database = new RiddlesDataSource(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);

		Intent intent = getIntent();
		riddleID      = intent.getLongExtra(MyAdapter.RIDDLES_ID, -1);
		currentRiddle = intent.getIntExtra(MyAdapter.CURRENT_RIDDLE, -1);
		skipWasUsed   = intent.getBooleanExtra(MyAdapter.SKIP_USED, false);
				
		//database.open();
		HttpGetList response = new HttpGetList();
		response.execute(riddleID);
		try {
			riddles = response.get().get(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		hintButton  = (Button) findViewById(R.id.hintButton);
		checkButton = (Button) findViewById(R.id.checkButton);
		skipButton  = (Button) findViewById(R.id.skipButton);

		hintButton.setOnClickListener(this);
		checkButton.setOnClickListener(this);
		skipButton.setOnClickListener(this);
		
		if(skipWasUsed)
			DisableSkip();
		
		RiddleTextView();
	}

	private void DisableSkip() {
		skipButton.setEnabled(false);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	  super.onSaveInstanceState(savedInstanceState);

	  savedInstanceState.putBoolean("Hint_Shows", hintIsShowing);
	  
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	  super.onRestoreInstanceState(savedInstanceState);

	  hintIsShowing = savedInstanceState.getBoolean("Hint_Shows");
	  if(hintIsShowing)
		  hint();
	}
	
	private void RiddleTextView() {
		textViewRiddle = (TextView) findViewById(R.id.riddleTextView);
		textViewTitle = (TextView) findViewById(R.id.riddleTitle);
		switch (currentRiddle) {
		case 1:
			textViewRiddle.setText("\"" + riddles.getRiddleone() + "\"");
			textViewTitle.setText(R.string.firstriddle);
			break;
		case 2:
			textViewRiddle.setText("\"" + riddles.getRiddletwo() + "\"");
			textViewTitle.setText(R.string.secondriddle);
			break;
		case 3:
			textViewRiddle.setText("\"" + riddles.getRiddlethree() + "\"");
			textViewTitle.setText(R.string.thirdriddle);
			break;
		}

	}

	private void checkForGPS() {
		// create class object
		gps = new GPSTracker(this);
		//gps.getLocation();
		// check if GPS enabled
		if (gps.canGetLocation()) {

			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();

			// \n is for new line
			Toast.makeText(getApplicationContext(),
					"Your Location is - \nLat: "   +latitude + 
										"\nLong: " + longitude, 
					Toast.LENGTH_LONG).show();

			double riddlelocationLong= 0;	
			double riddlelocationLat = 0;
			
			switch (currentRiddle) {
			case 1:
				riddlelocationLong = riddles.getRiddleonelocationLong();
				riddlelocationLat = riddles.getRiddleonelocationLat();
				break;
			case 2:
				riddlelocationLong = riddles.getRiddletwolocationLong();
				riddlelocationLat = riddles.getRiddletwolocationLat();
				break;
			case 3:
				riddlelocationLong = riddles.getRiddlethreelocationLong();
				riddlelocationLat = riddles.getRiddlethreelocationLat();
				break;
			}
			//Log.e("locatoin" , riddlelocationstring[0] + riddlelocationstring[1]);
			
																																		//Tolerance
			if (DistanceBetweenTwo(latitude, longitude, riddlelocationLong, riddlelocationLat) < .09) {
				if(currentRiddle == 3){
					AlertUserOfFinishedSequence();
				}
				else{
					AlertUserOfNextChallenge();
				}
			}
			gps.stopUsingGPS();
		} else {
			gps.showSettingsAlert();
		}

	}

	private void AlertUserOfNextChallenge() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle("Congratulations");

		// Setting Dialog Message
		alertDialog.setMessage("You Solved Riddle Number " + currentRiddle
				+ " of 3");

		// add points
        HttpRequests response = new HttpRequests();
        int points = 2;
        if(skipWasUsed)
        	points = 0;
        if(hintIsShowing)
        	points = 1;
        response.createAddPoints(points);
        response.execute();
		
		// on pressing cancel button
		alertDialog.setNegativeButton("COOL",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						nextRiddle();
						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();

	}
	
	private void AlertUserOfFinishedSequence() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle("Congratulations");

		// Setting Dialog Message
		alertDialog.setMessage("You Solved All The Riddles In The Sequence");

		// on pressing cancel button
		alertDialog.setNegativeButton("COOL",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();

	}

	private void nextRiddle() {
		Intent intent = new Intent(this, PlayActivity.class);
		intent.putExtra(MyAdapter.RIDDLES_ID, riddleID);
		currentRiddle++;
		Log.i("current riddle", "" + currentRiddle);
		intent.putExtra(MyAdapter.CURRENT_RIDDLE, currentRiddle);
		intent.putExtra(MyAdapter.SKIP_USED, skipWasUsed);
		startActivity(intent);
		this.finish();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.hintButton:
			confirmHintAlert();
			break;
		case R.id.checkButton:
			checkAnswer();
			break;
		case R.id.skipButton:
			skip();
			break;
		}
	}

	private void skip() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle("Skip");

		// Setting Dialog Message
		alertDialog.setMessage("Do you really want to skip riddle " + currentRiddle + "?\nThis is the only one you get." );
				//+ " you will lose your chance of a medal");

		// on pressing cancel button
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		
		alertDialog.setPositiveButton("Yes Skip", 
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						skipWasUsed = true;
						nextRiddle();
						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();

	}

	private void checkAnswer() {
		checkForGPS();
	}

	private void confirmHintAlert(){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Hint");
		alertDialog.setMessage("Are you sure you want to \nuse a hint for riddle " + currentRiddle + "?" );
				//+ " you will lose your chance of a medal");
		alertDialog.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		
		alertDialog.setPositiveButton("Yes Use Hint", 
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						hint();
						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}
	
	
	
	private void hint() {
		hintIsShowing = true;
		hintButton.setEnabled(false);
		textViewHint = (TextView) findViewById(R.id.riddleHintTextView);
		switch (currentRiddle) {
		case 1:
			textViewHint.setText("\"" + riddles.getRiddleonehint() + "\"");
			break;
		case 2:
			textViewHint.setText("\"" + riddles.getRiddletwohint() + "\"");
			break;
		case 3:
			textViewHint.setText("\"" + riddles.getRiddlethreehint() + "\"");
			break;
		}
		
		
	}
	
	private double DistanceBetweenTwo(double aLat1, double aLong1, double aLat2, double aLongi2) {
	  	double earthRadius = 3958.75;
	    double dLat = Math.toRadians(aLat2-aLat1);
	    double dLng = Math.toRadians(aLongi2-aLong1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(aLat1)) * Math.cos(Math.toRadians(aLat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = earthRadius * c;

	    int meterConversion = 1609;
	    
	    return (dist * meterConversion) * 0.00062137119; //returns miles
		
	}

}
