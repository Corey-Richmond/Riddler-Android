package com.richmond.riddler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.richmond.riddler.http.HttpGetUserInfo;

public class MenuActivity extends SherlockActivity implements OnClickListener {

	private Button profileButton, 
				   createRiddleButton, 
				   riddlesButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        
        profileButton      = (Button) findViewById(R.id.profile);
        createRiddleButton = (Button) findViewById(R.id.createriddle);
        riddlesButton      = (Button) findViewById(R.id.riddles);
        
        profileButton.setOnClickListener(this);
        createRiddleButton.setOnClickListener(this);
        riddlesButton.setOnClickListener(this);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
             
    	HttpGetUserInfo info = new HttpGetUserInfo(this);
    	info.execute(getIntent().getStringExtra(Web.USERNAME));


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
        case android.R.id.home:
            Intent intent = new Intent(this, LogonActivity.class);            
            intent.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME); 
            startActivity(intent); 
          return(true);
      }
      // more code here for other cases
      return false;
    }
  
    
	public void onClick(View v) {
		switch(v.getId()) {
	        case R.id.profile:
	        	Profile();
	        	break;
	        case R.id.createriddle:
	        	CreateRiddle();
	        	break;
	        case R.id.riddles:
	        	PlayRiddles();
	        	break;
		}
		
	}
	private void Profile() {
		Intent intent = new Intent(this, ProfileActivity.class);
		startActivity(intent);
	}
	

	private void PlayRiddles() {
		Intent intent = new Intent(this, PlayableRiddlesListActivity.class);
    	startActivity(intent);
	}

	private void CreateRiddle() {
		Intent intent = new Intent(this, CreateRiddleActivity.class);
    	startActivity(intent);
	}

}
