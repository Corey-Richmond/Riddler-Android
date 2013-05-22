package com.richmond.riddler;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ProfileActivity extends Activity {
	private TextView mName;
	private TextView mEmail;
	private TextView mPoints;
	private User mUser;
	//HttpGetUserInfo response;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mUser = new User();
        mUser.loadSerializedObject();
		Log.i("user", mUser.toString());
        mName = (TextView) findViewById(R.id.name);
        mName.setText(mUser.getFirstName());
        mEmail = (TextView) findViewById(R.id.username);
        mEmail.setText(mUser.getUserName());
        mPoints = (TextView) findViewById(R.id.numofpoints);
        mPoints.setText(mUser.getPoints()+"");
        
    }
    

}
