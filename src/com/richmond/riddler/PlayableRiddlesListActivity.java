package com.richmond.riddler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.richmond.riddler.http.HttpGetListOfRiddles;

public class PlayableRiddlesListActivity extends Activity{ 
    //private RiddlesDataSource datasource;
    private ListView mList;
    MyAdapter adapter; 
	GPSTracker gps;
	double latitude, longitude;
	ProgressDialog dialog;
	
	List<RiddleSequence> values;
	List<RiddleSequence> valuesSorted = new LinkedList<RiddleSequence>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playable_riddles_list);

        dialog = ProgressDialog.show(PlayableRiddlesListActivity.this, "", 
                "Loading. Please wait...", true);
        
        HttpGetListOfRiddles requests = new HttpGetListOfRiddles();

		try {
			values = requests.execute().get();
			//Log.i("values", values.indexOf(0))
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//addDistancetoValues();
		//sortValues();
		
		adapter = new MyAdapter(this, R.layout.listview_item_row, values); 
		
		mList = (ListView)findViewById(R.id.listView1);
		
		mList.setAdapter(adapter);
		dialog.dismiss();
	}



	private void sortValues() {	
    	while(!values.isEmpty()){   
    		RiddleSequence temp = findMin();
    		valuesSorted.add(temp);
    		remove(temp);
    	}		
	}

	private RiddleSequence findMin() {
        Iterator<RiddleSequence> iter = values.iterator();
       	if (values.isEmpty())  
       		return null;
       	RiddleSequence min = values.get(0);  
    	while(iter.hasNext()){  
    		RiddleSequence temp = iter.next();  
    		if( Double.valueOf(temp.getDistance()) < Double.valueOf(min.getDistance())){  
    			min=temp;   
    		}
    	}
		return min;
    }
	
    public boolean remove(RiddleSequence o) {
    	int index = values.indexOf(o);
    	
    	if (index == -1)
		return false;
    	
    	values.remove(index);
    	return true;
    }


//	private void addDistancetoValues() {
//		getLocation();
//		double additionalDistance, totalDistance;
//		
//	        for(int i=0; i < values.size(); i++){
//	        	 additionalDistance = DistanceBetweenTwo(Double.valueOf(values.get(i).getRiddleonelocation().split(",")[0]), 
//     				   Double.valueOf(values.get(i).getRiddleonelocation().split(",")[1]), 
//     				   latitude, longitude);
//	        	totalDistance = (Double.valueOf(values.get(i).getDistance()) + additionalDistance);
//	        	values.get(i).setDistance(String.valueOf(totalDistance));
//	        }
//	}



	@Override
	  protected void onResume() {
	   // datasource.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	   // datasource.close();
	    super.onPause();
	  }


	    
	    private void getLocation() {
	  		// create class object
	  		gps = new GPSTracker(this);

	  		// check if GPS enabled
	  		if (gps.canGetLocation()) {

	  			latitude = gps.getLatitude();
	  			longitude = gps.getLongitude();

	  			// \n is for new line
	  			Toast.makeText(getBaseContext(),
	  					"Your Location is - \nLat: "   +latitude + 
	  										"\nLong: " + longitude, 
	  					Toast.LENGTH_LONG).show();
	  			
	  			gps.stopUsingGPS();
	  		} else {
	  			gps.showSettingsAlert();
	  		}

	    }
	    
	    private double DistanceBetweenTwo(double aLat1, double aLong1, double aLat2, double aLongi2) {
	  	  	double earthRadius = 3958.75;
	  	    double dLat = Math.toRadians(aLat2-aLat1);
	  	    double dLng = Math.toRadians(aLongi2-aLong1);
	  	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	  	               Math.cos(Math.toRadians(aLat1)) * Math.cos(Math.toRadians(aLat2)) *
	  	               Math.sin(dLng/2) * Math.sin(dLng/2);
	  	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	  	    double dist = earthRadius * c;

	  	    int meterConversion = 1609;
	  	    
	  	    return (dist * meterConversion) * 0.00062137119; //returns miles
	  		
	    }

}

