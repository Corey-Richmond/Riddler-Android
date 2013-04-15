package com.richmond.riddler;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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
	
    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";
    public static final String EXTRA_ACCOUNTNAME = "extra_accountname";

    private AccountManager mAccountManager;

    private Spinner mAccountTypesSpinner;

    private TextView mOut;

    static final int REQUEST_CODE_RECOVER_FROM_AUTH_ERROR = 1001;
    static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1002;

    private String[] mNamesArray;
    private String mEmail;

    private Type requestType;

    public static String TYPE_KEY = "type_key";
    public static enum Type {FOREGROUND, BACKGROUND, BACKGROUND_WITH_SYNC}
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);

        mNamesArray = getAccountNames();
        mAccountTypesSpinner = initializeSpinner(
                R.id.spinner1, mNamesArray);

//        Bundle extras = getIntent().getExtras();
        requestType = Type.BACKGROUND;
       // setTitle(getTitle() + " - " + requestType.name());
        initializeFetchButton();
//        if (extras.containsKey(EXTRA_ACCOUNTNAME)) {
//            mEmail = extras.getString(EXTRA_ACCOUNTNAME);
//            mAccountTypesSpinner.setSelection(getIndex(mNamesArray, mEmail));
//            new GetNameInBackground(LogonActivity.this, mEmail, SCOPE, REQUEST_CODE_RECOVER_FROM_AUTH_ERROR)
//                    .execute();
//        }

    }


	public void onClick(View v) {
		switch(v.getId()) {
	        case R.id.login:
	        	Login();
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


	public void Login(){
    	Intent intent = new Intent(this, MenuActivity.class);
    	startActivity(intent);
    }
    private Spinner initializeSpinner(int id, String[] values) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LogonActivity.this,
                android.R.layout.simple_spinner_item, values);
        Spinner spinner = (Spinner) findViewById(id);
        spinner.setAdapter(adapter);
        return spinner;
    }

	
    private String[] getAccountNames() {
        mAccountManager = AccountManager.get(this);
        Account[] accounts = mAccountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        String[] names = new String[accounts.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = accounts[i].name;
        }
        return names;
    }
    
    private void initializeFetchButton() {
        Button getToken = (Button) findViewById(R.id.login);
        getToken.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int accountIndex = mAccountTypesSpinner.getSelectedItemPosition();
                if (accountIndex < 0) {
                    // this happens when the sample is run in an emulator which has no google account
                    // added yet.
                    //show("No account available. Please add an account to the phone first.");
                    return;
                }
                mEmail = mNamesArray[accountIndex];
                //new GetNameInBackground(LogonActivity.this, mEmail, SCOPE, REQUEST_CODE_RECOVER_FROM_AUTH_ERROR).execute();
                Login();
            }
        });
    }
    
//    public void show(final String message) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mOut.setText(message);
//            }
//        });
//    }
    private int getIndex(String[] array, String element) {
        for (int i = 0; i < array.length; i++) {
            if (element.equals(array[i])) {
                return i;
            }
        }
        return 0;  // default to first element.
    }
}
