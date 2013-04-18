package com.richmond.riddler;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class ProfileActivity extends Activity {
	private TextView mName;
	private TextView mEmail;
	private TextView mPoints;
	HttpGetUserInfo response;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mName = (TextView) findViewById(R.id.name);
        mName.setText(AbstractGetNameTask.getName());
        mEmail = (TextView) findViewById(R.id.username);
        mEmail.setText(getIntent().getStringExtra(Web.USERNAME));
        mPoints = (TextView) findViewById(R.id.numofpoints);
        mPoints.setText("" + getIntent().getIntExtra(Web.POINTS, 0));
        
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_profile, menu);
        return true;
    }
}
