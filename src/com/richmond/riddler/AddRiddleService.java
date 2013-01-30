package com.richmond.riddler;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class AddRiddleService extends Service {
	private RiddlesDataSource datasource;
	
	@Override
	    public IBinder onBind(Intent intent) {
	        return null;
	    }

	    @Override
	    public void onCreate() {
	        super.onCreate();
	        //Toast.makeText(this,"Service Created",Toast.LENGTH_LONG).show();


	    }

	    @Override
	    public void onDestroy() {
	        super.onDestroy();
	        Toast.makeText(this,"Service Destroy",Toast.LENGTH_LONG).show();
	    }

	    @Override
	    public void onLowMemory() {
	        super.onLowMemory();
	        Toast.makeText(this,"Service LowMemory",Toast.LENGTH_LONG).show();
	    }

	    @Override
	    public void onStart(Intent intent, int startId) {
	        //super.onStart(intent, startId);
	        //Toast.makeText(this,"Service start",Toast.LENGTH_LONG).show();
	        String[] riddlesAndHints = intent.getStringArrayExtra(CreateRiddleActivity.RIDDLESANDHINTS);
	        double[] locations = intent.getDoubleArrayExtra(CreateRiddleActivity.LOCATION);
	        double distance = intent.getDoubleExtra(CreateRiddleActivity.DISTANCE, 0);
	        datasource = new RiddlesDataSource(this);
	        datasource.open();
	        datasource.createRiddleSequence(riddlesAndHints, locations, distance);
	        Toast.makeText(this, "Successfully Added A Riddle Sequence",Toast.LENGTH_LONG).show();

	    }



}
