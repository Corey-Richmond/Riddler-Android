package com.richmond.riddler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class CreateRiddleFragment extends SherlockFragment{


	private Button doneButton, pinRiddleOne;
	private EditText riddle1, riddle1hint;
	private double lat1, longi1;
	private int riddleNum;
	
	private onFinishedRiddleListener mListener;
	
	private AlertDialog mAlertDialog;
	public static final int DIALOG_CREATE_RIDDLE_FAILURE = 1;
	public static final int DIALOG_TITLE_ERROR = 2;
	public static final int DIALOG_RIDDLE_ERROR = 3;
	public static final int DIALOG_HINT_ERROR = 4;
	public static final int DIALOG_LOCATION_ERROR = 5;
	
	public CreateRiddleFragment(int riddleNum) {
		this.riddleNum = riddleNum;
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
        try {
            mListener = (onFinishedRiddleListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onFinishedRiddleListener");
        }
	}
	
	public interface onFinishedRiddleListener {
		public void onFinishedRiddle(int riddleNumber, String riddle, String hint, double longi, double lat);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 View rootView = inflater.inflate(R.layout.create_riddle_fragment, container, false);
			doneButton = (Button) rootView.findViewById(R.id.donebutton);
			pinRiddleOne = (Button) rootView.findViewById(R.id.pinfirstriddle);


			riddle1 = (EditText) rootView.findViewById(R.id.riddle1);
			riddle1hint = (EditText) rootView.findViewById(R.id.riddle1hint);

			pinRiddleOne.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    LaunchMapView();
                }
            });
			
			doneButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Done();
                }


            });
		 return rootView;
	}
	
	private void Done() {
		if (validateFields()) {
			mListener.onFinishedRiddle(riddleNum, riddle1.getText().toString(), riddle1hint.getText().toString(), longi1, lat1);		
		}
		
	}
	
	private boolean validateFields() {
		if (!validateRiddles()) {
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
	
	private boolean validateRiddles() {
		Log.i("riddle1_text", riddle1.getText().toString());
		return (riddle1.getText().toString().length() != 0);
	}

	private boolean validateHints() {
		return (riddle1hint.getText().toString().length() != 0);
	}

	private boolean validateLocations() {
		return (lat1 != 0 && longi1 != 0 );
	}
	
	public Dialog showDialog(int aId) {
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
		return null;
	}
	
	private Dialog createTryAgainDialog(int aMessage) {
		mAlertDialog = new AlertDialog.Builder(getActivity())
				.setMessage(getString(aMessage))
				.setNegativeButton("Try Again", null).setCancelable(true)
				.show();

		return mAlertDialog;
	}


	
	private void LaunchMapView() {
		Intent intent = new Intent(getActivity(), LocationSelectionActivity.class);
		startActivityForResult(intent, riddleNum);

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		Log.i("Result", "HERE ******");
		
				longi1 = data.getDoubleExtra("resultLongitude", 1);
				lat1 = data.getDoubleExtra("resultLatitude", 1);
				Toast t = Toast.makeText(getActivity(), "longi " + longi1
					+ "lat " + lat1, Toast.LENGTH_LONG);
				t.show();
		

	}// onAcrivityResult

	
}
