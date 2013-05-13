package com.richmond.riddler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.richmond.riddler.http.HttpGetUserInfo;

public class MenuActivity extends Activity implements OnClickListener {

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
             
    	HttpGetUserInfo info = new HttpGetUserInfo(this);
    	info.execute(getIntent().getStringExtra(Web.USERNAME));


    }

    

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
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
