package com.richmond.riddler;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.richmond.riddler.CreateRiddleFragment.onFinishedRiddleListener;
import com.richmond.riddler.CreateRiddleSubmitFragment.onFinishedRiddleSequenceListener;
import com.richmond.riddler.http.HttpPostRiddle;

public class CreateRiddleActivity extends SherlockFragmentActivity implements onFinishedRiddleListener, onFinishedRiddleSequenceListener{

	private AlertDialog mAlertDialog;
	public static final int DIALOG_CREATE_RIDDLE_FAILURE = 1;
	public static final int DIALOG_TITLE_ERROR = 2;
	public static final int DIALOG_RIDDLE_ERROR = 3;
	public static final int DIALOG_HINT_ERROR = 4;
	public static final int DIALOG_LOCATION_ERROR = 5;

	public final static String RIDDLESANDHINTS = "com.richmond.riddler.MESSAGE";
	public final static String DISTANCE = "com.richmond.riddler.DISTANCE";
	public final static String LOCATION = "com.richmond.riddler.LOCATION";


	private String riddle1, riddle2, riddle3, riddleTitle, riddle1hint,
			riddle2hint, riddle3hint;
	private double lat1, longi1, lat2, longi2, lat3, longi3, distance;
	private ActionBar actionBar;
	RiddleSequence riddles;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_riddle_main);
		
		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setTitle("Create Riddles");
		
		ActionBar.Tab Riddle1Tab = actionBar.newTab().setText("Riddle 1");
		ActionBar.Tab Riddle2Tab = actionBar.newTab().setText("Riddle 2");
		ActionBar.Tab Riddle3Tab = actionBar.newTab().setText("Riddle 3");
		
		Fragment frag1 = new CreateRiddleFragment(1);
		Fragment frag2 = new CreateRiddleFragment(2);
		Fragment frag3 = new CreateRiddleFragment(3);
		
		Riddle1Tab.setTabListener(new MyTabListener(frag1));
		Riddle2Tab.setTabListener(new MyTabListener(frag2));
		Riddle3Tab.setTabListener(new MyTabListener(frag3));
		
		actionBar.addTab(Riddle1Tab);
		actionBar.addTab(Riddle2Tab);
		actionBar.addTab(Riddle3Tab);
		
		riddles = new RiddleSequence();
		
	

	}
	
	
	class MyTabListener implements ActionBar.TabListener{
		public Fragment fragment;
		
		public MyTabListener( Fragment fragment){
			this.fragment = fragment;
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			ft.replace(R.id.fragment_container, fragment);
			
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}
		
	}


	
//	private void AddRiddleSequence(View v) {
//		if (validateFields()) {
//			
//			double[] locations = { lat1,longi1,lat2,longi2,lat3,longi3};
//			CalculateTotalDistance(locations);
//			RiddleSequence riddles = new RiddleSequence("", riddleTitle.getText()
//					.toString(), riddle1.getText().toString(), riddle2
//					.getText().toString(), riddle3.getText().toString(),
//					riddle1hint.getText().toString(), riddle2hint.getText()
//							.toString(), riddle3hint.getText().toString(),
//					longi1, lat1, longi2, lat2, longi3, lat3, distance, AbstractGetNameTask.getmEmailAddress());
//			
//	        HttpPostRiddle post = new HttpPostRiddle(this, riddles);
//	        post.execute();
//		}
//	}
	
//	@SuppressWarnings("deprecation")
//	private boolean validateFields() {
//		if (!validateTitle()) {
//			showDialog(DIALOG_TITLE_ERROR);
//		} else if (!validateRiddles()) {
//			showDialog(DIALOG_RIDDLE_ERROR);
//		} else if (!validateHints()) {
//			showDialog(DIALOG_HINT_ERROR);
//		} else if (!validateLocations()) {
//			showDialog(DIALOG_LOCATION_ERROR);
//		} else {
//			return true;
//		}
//		return false;
//	}

//	private boolean validateTitle() {
//		return (riddleTitle.getText().toString().length() != 0);
//	}
//
//	private boolean validateRiddles() {
//		Log.i("riddle1_text", riddle1.getText().toString());
//		return (riddle1.getText().toString().length() != 0
//				&& riddle2.getText().toString().length() != 0 && riddle3
//				.getText().toString().length() != 0);
//	}
//
//	private boolean validateHints() {
//		return (riddle1hint.getText().toString().length() != 0
//				&& riddle2hint.getText().toString().length() != 0 && riddle3hint
//				.getText().toString().length() != 0);
//	}
//
//	private boolean validateLocations() {
//		return (lat1 != 0 && longi1 != 0 && lat2 != 0 && longi2 != 0
//				&& lat3 != 0 && longi3 != 0);
//	}

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

	@Override
	public void onFinishedRiddle(int riddleNumber, String riddle, String hint, double longi, double lat) {

		if(riddleNumber == 1){
			riddles.setRiddleone(riddle);
			riddles.setRiddleonehint(hint);
			riddles.setRiddleonelocationLong(longi);
			riddles.setRiddleonelocationLat(lat);
		}
		if(riddleNumber == 2){
			riddles.setRiddletwo(riddle);
			riddles.setRiddletwohint(hint);
			riddles.setRiddletwolocationLong(longi);
			riddles.setRiddletwolocationLat(lat);
		}
		if(riddleNumber == 3){
			riddles.setRiddlethree(riddle);
			riddles.setRiddlethreehint(hint);
			riddles.setRiddlethreelocationLong(longi);
			riddles.setRiddlethreelocationLat(lat);
		}
		
		if(actionBar.getTabCount() != 1)
			actionBar.removeTab(actionBar.getSelectedTab());
		else{
			ActionBar.Tab Riddle1Tab = actionBar.newTab().setText("Title And Submit");
			
			Fragment frag1 = new CreateRiddleSubmitFragment();
		
			Riddle1Tab.setTabListener(new MyTabListener(frag1));
			
			actionBar.addTab(Riddle1Tab);
			actionBar.removeTab(actionBar.getSelectedTab());
			double[] locations = { riddles.getRiddleonelocationLat(),riddles.getRiddleonelocationLong(),
					riddles.getRiddletwolocationLat(),riddles.getRiddletwolocationLong(),
					riddles.getRiddlethreelocationLat(),riddles.getRiddlethreelocationLong()};
			CalculateTotalDistance(locations);
			riddles.setDistance(distance);
		}
				
			
		
	}


	@Override
	public void onFinishedRiddleSequence(String title) {
		riddles.setId("");
		riddles.setRiddletitle(title);
		riddles.setCreatedby(AbstractGetNameTask.getmEmailAddress());

		HttpPostRiddle post = new HttpPostRiddle(this, riddles);
		post.execute();
		
	}

}
