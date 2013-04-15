package com.richmond.riddler;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableNotifiedException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

public class LogonActivity extends Activity implements OnClickListener {
	
	private Button loginButton, registerButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);
        
        loginButton    = (Button) findViewById(R.id.login);
        registerButton = (Button) findViewById(R.id.register);
        
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }


	public void onClick(View v) {
		switch(v.getId()) {
	        case R.id.login:
	        	Login(v);
	        	break;
	        case R.id.register:
	        	Register(v);
	        	break;
		}
		
	}
	
    private void Register(View aView){
    	Intent intent = new Intent(this, RegisterActivity.class);
    	startActivity(intent);
    }


	public void Login(View aView){
    	Intent intent = new Intent(this, MenuActivity.class);
    	startActivity(intent);
    }
    
}
