package com.richmond.riddler;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesUtil;

public class CheckForGooglePlayServices{

	public CheckForGooglePlayServices(Context c) {
		super();
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(c);
		if(status != 0){
			Log.e("GooglePlayServiceError", status+"");
		}
		
	}
	
	
	
}
