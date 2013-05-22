package com.richmond.riddler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.richmond.riddler.http.HttpGetRiddleFromId;
import com.richmond.riddler.http.HttpRequests;
import com.richmond.riddler.http.HttpUpdateUserInfo;

public class PlayActivity extends FragmentActivity implements OnClickListener, ConnectionCallbacks,
OnConnectionFailedListener, LocationListener, OnAddGeofencesResultListener {

	private User mUser;
	private int listIndex;
	private GPSTracker gps;
	boolean skippedThisRiddle;
	private RiddleSequence riddles;
	private RiddlesStarted mRiddleStarted;
	private Button hintButton, checkButton, skipButton;
	private TextView textViewRiddle, textViewHint, textViewTitle;
	private LocationClient mLocationClient;
	private Location mCurrentLocation;
	private LocationRequest mLocationRequest;
	private Geofence gf;
	double riddlelocationLong = 0;
	double riddlelocationLat = 0;
	private IntentFilter filter;

	public BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			increaseUpdateLocationRate();
		    Toast.makeText(context, "YOUR CLOSE... IN GEOFENCE",Toast.LENGTH_LONG).show();
		        // Vibrate the mobile phone
		        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		        vibrator.vibrate(2000);
		}


	};
	
	private void increaseUpdateLocationRate() {
        mLocationRequest.setPriority(
                LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Set the update interval to 15 seconds
        mLocationRequest.setInterval(5000);
        // Set the fastest update interval to 1 second
        mLocationRequest.setFastestInterval(1000);
        mLocationClient.requestLocationUpdates(mLocationRequest, this );	
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		setOnClickListenerForButtons();
		setupCurrentRiddle();
		RiddleTextView();
	

        
		skippedThisRiddle = false;
		if (mRiddleStarted.isSkip())
			DisableSkip();

		if(mRiddleStarted.isHint()){
			hint();
		}
		
		getRiddlesLocation();


		mLocationClient = new LocationClient(this, this, this);
		
		gf = new Geofence.Builder()
                    .setRequestId("1")
                    .setTransitionTypes(1)
                    .setCircularRegion(
                    		riddlelocationLat, riddlelocationLong, 100)
                    .setExpirationDuration(-1)
                    .build();
		
		filter = new IntentFilter("Toast");
		
	
	}
	
	private void getRiddlesLocation() {
		switch (mRiddleStarted.getCurrentRiddle()) {
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
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.registerReceiver(receiver, filter);
	}

	@Override
	protected void onPause() {
		this.unregisterReceiver(receiver);
		updateUserInfo();
		super.onPause();
	}
	
    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mLocationClient.connect();
        
    }
    
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onStop();
    }
	
	
	private void setOnClickListenerForButtons() {
		hintButton = (Button) findViewById(R.id.hintButton);
		checkButton = (Button) findViewById(R.id.checkButton);
		skipButton = (Button) findViewById(R.id.skipButton);

		hintButton.setOnClickListener(this);
		checkButton.setOnClickListener(this);
		skipButton.setOnClickListener(this);
	}


	private void setupCurrentRiddle() {
		// initializes variables
		listIndex = -1;
		mUser = new User();

		// gets intent info
		String riddleID = getIntent().getStringExtra(MyAdapter.RIDDLES_ID);


		// gets current user info
		mUser.loadSerializedObject();
		Log.i("user", mUser.toString());

		// 
		int currentRiddle = getIntent().getIntExtra(MyAdapter.CURRENT_RIDDLE, -1);
		boolean intentSkip = getIntent().getBooleanExtra(MyAdapter.SKIP_USED, false);
		mRiddleStarted = new RiddlesStarted(riddleID, currentRiddle , false, intentSkip, false, false);
		
		// get and set current riddle info
		handleCurrentRiddle();
		
		// gets riddle info
		HttpGetRiddleFromId response = new HttpGetRiddleFromId(riddleID);
		response.execute();
		try {
			riddles = response.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
	}
	
	private void handleCurrentRiddle() {
		int currentRiddle = getIntent().getIntExtra(MyAdapter.CURRENT_RIDDLE, -1);
		List<RiddlesStarted> started = mUser.getRiddlesStarted();
		if ((listIndex = mRiddleStarted.isIn(started)) != -1) {
			if(currentRiddle <= started.get(listIndex).getCurrentRiddle())
				mRiddleStarted = started.get(listIndex);
			else
				updateUserInfo();
		}
		else
			addRiddleToRiddlesStarted();
		
		mUser.saveUser();
		listIndex = mRiddleStarted.isIn(started);
	}

	private void addRiddleToRiddlesStarted() {
		mUser.getRiddlesStarted().add(mRiddleStarted);
		HttpUpdateUserInfo updateInfo = new HttpUpdateUserInfo(
				mRiddleStarted,
				(Web.BASE_URL + Web.UPDATEUSER + Web.RIDDLESSTARTED
						+ "/" + mUser.getUserName()), 0);
		updateInfo.execute();
	}


	private void updateUserInfo() {
		if ((listIndex = mRiddleStarted.isIn(mUser.getRiddlesStarted())) != -1) {
			mUser.getRiddlesStarted().remove(listIndex);
			mUser.getRiddlesStarted().add(mRiddleStarted);
		}
		mUser.saveUser();
		HttpUpdateUserInfo updateInfo = new HttpUpdateUserInfo(
				mRiddleStarted,
				(Web.BASE_URL + Web.UPDATEUSER + Web.RIDDLESSTARTED
						+ "/" + mUser.getUserName()), 0);
		updateInfo.execute();	
	}

	private void DisableSkip() {
		skipButton.setEnabled(false);
	}

	private void RiddleTextView() {
		textViewRiddle = (TextView) findViewById(R.id.riddleTextView);
		textViewTitle = (TextView) findViewById(R.id.riddleTitle);
		switch (mRiddleStarted.getCurrentRiddle()) {
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



	private void AlertUserOfNextChallenge() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle("Congratulations");

		// Setting Dialog Message
		alertDialog.setMessage("You Solved Riddle Number " + mRiddleStarted.getCurrentRiddle()
				+ " of 3");

		// add points
		addPoints();

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

	private void addPoints() {
		int points = 2;
		if (mRiddleStarted.isHint())
			points = 1;
		if (skippedThisRiddle)
			points = 0;
		mUser.setPoints(mUser.getPoints() + points);
		mUser.saveUser();
		HttpRequests response = new HttpRequests(points);
		response.execute();
	}


	private void AlertUserOfFinishedSequence() {
		
		addPoints();
		if(mRiddleStarted.isSkip())
			mRiddleStarted.setFinishedWithSkip(true);
		else
			mRiddleStarted.setFinishedWithoutSkip(true);
		updateUserInfo();
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
		intent.putExtra(MyAdapter.RIDDLES_ID, mRiddleStarted.getId());
		intent.putExtra(MyAdapter.CURRENT_RIDDLE, mRiddleStarted.getCurrentRiddle()+1);
		intent.putExtra(MyAdapter.SKIP_USED, mRiddleStarted.isSkip());
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
		skippedThisRiddle = true;
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle("Skip");

		// Setting Dialog Message
		alertDialog.setMessage("Do you really want to skip riddle "
				+ mRiddleStarted.getCurrentRiddle() + "?\nThis is the only one you get.");
		// + " you will lose your chance of a medal");

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
						mRiddleStarted.setSkip(true);
						updateUserInfo();
						if(mRiddleStarted.getCurrentRiddle() == 3)
							AlertUserOfFinishedSequence();	
						else
							nextRiddle();	
						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();
		mUser.saveUser();

	}

	private void checkAnswer() {
		mCurrentLocation = mLocationClient.getLastLocation();
		
		Toast.makeText(
				getApplicationContext(),
				"Your Location is - \nLat: " + mCurrentLocation.getLatitude() + "\nLong: "
						+ mCurrentLocation.getLongitude(), Toast.LENGTH_LONG).show();
		
		getRiddlesLocation();
		
		if (DistanceBetweenTwo(mCurrentLocation.getLatitude() ,mCurrentLocation.getLongitude(),
				riddlelocationLat, riddlelocationLong) < 65) {
			if (mRiddleStarted.getCurrentRiddle() == 3)
				AlertUserOfFinishedSequence();
		    else 
				AlertUserOfNextChallenge();
		}

	}

	private void confirmHintAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Hint");
		alertDialog
				.setMessage("Are you sure you want to \nuse a hint for riddle "
						+ mRiddleStarted.getCurrentRiddle() + "?");
		// + " you will lose your chance of a medal");
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
		mRiddleStarted.setHint(true);
		updateUserInfo();
		hintButton.setEnabled(false);
		textViewHint = (TextView) findViewById(R.id.riddleHintTextView);
		switch (mRiddleStarted.getCurrentRiddle()) {
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

		return (dist * meterConversion); // returns meters

	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		updateUserInfo();
	}


	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (result.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
            	result.startResolutionForResult( this, 0);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            //showErrorDialog(result.getErrorCode());
        	Toast.makeText(this, result.getErrorCode()+"", Toast.LENGTH_SHORT).show();
        }	
	}


	@Override
	public void onConnected(Bundle connectionHint) {
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();

		Intent intent = new Intent("Toast");
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, intent, 0);
		List<Geofence> gfs = new ArrayList<Geofence>();
		gfs.add(gf);
		mLocationClient.addGeofences(gfs, pendingIntent, this);
        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create();
        // Use high accuracy
        mLocationRequest.setPriority(
                LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        // Set the update interval to 15 seconds
        mLocationRequest.setInterval(15000);
        // Set the fastest update interval to 1 second
        mLocationRequest.setFastestInterval(1000);
        
        mLocationClient.requestLocationUpdates(mLocationRequest, this);
		
	}


	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onLocationChanged(Location location) {
        // Report to the UI that the location was updated
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onAddGeofencesResult(int arg0, String[] arg1) {
		// TODO Auto-generated method stub
		
	}
	

}
