package com.richmond.riddler;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

public class AddRiddleService extends IntentService {
	//private RiddlesDataSource datasource;
	Handler mHandler;
	
	public AddRiddleService(){
		super("AddRiddleService");
	}
	@Override
	public void onCreate() {
	    super.onCreate();
	    mHandler = new Handler();
	}; 

	@Override
	protected void onHandleIntent( Intent intent) {

        String[] riddlesAndHints = intent.getStringArrayExtra(CreateRiddleActivity.RIDDLESANDHINTS);
        double[] locations = intent.getDoubleArrayExtra(CreateRiddleActivity.LOCATION);
        double distance = intent.getDoubleExtra(CreateRiddleActivity.DISTANCE, 0);
        
//        datasource = new RiddlesDataSource(AddRiddleService.this);
//        datasource.open();
//        datasource.createRiddleSequence(riddlesAndHints, locations, distance);
        
        HttpRequests request = new HttpRequests();
        request.createPostRequest(riddlesAndHints, locations, distance);
        request.doInBackground();
        
        mHandler.post(new Runnable() {            
            @Override
            public void run() {
            	Toast.makeText(AddRiddleService.this, "Successfully Added A Riddle Sequence",Toast.LENGTH_LONG).show();              
            }
        });
        

	}



}
