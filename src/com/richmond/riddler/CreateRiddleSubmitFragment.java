package com.richmond.riddler;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;

public class CreateRiddleSubmitFragment extends SherlockFragment{
	
	private onFinishedRiddleSequenceListener mListener;
	private Button doneButton;
	private EditText title;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
        try {
            mListener = (onFinishedRiddleSequenceListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onFinishedRiddleListener");
        }
	}

	public interface onFinishedRiddleSequenceListener {
		public void onFinishedRiddleSequence(String title);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 View rootView = inflater.inflate(R.layout.create_riddle_submit, container, false);
		 doneButton = (Button) rootView.findViewById(R.id.donebutton);
		 title = (EditText) rootView.findViewById(R.id.title);
		 
		doneButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Done();
            }

        });
		 return rootView;
	}
	
	private void Done() {
		if(title.getText().toString() != null){
			mListener.onFinishedRiddleSequence(title.getText().toString());
		}
		
	}
	
}
