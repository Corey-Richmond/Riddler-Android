package com.richmond.riddler;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class LocationSelectionActivity extends MapActivity implements OnClickListener {

	MapController mControl;
	GeoPoint GeoP;
	MapView mapView;
	int x, y;
	GeoPoint touchedPoint;
	Drawable drawPin;
	List<Overlay> overlayList;
	double longitude=0, latitude=0;
	Button mapDone; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.activity_location_selection);
	    
        mapDone = (Button) findViewById(R.id.mapDone);

        mapDone.setOnClickListener(this);
	    
	    mapView = (MapView) findViewById(R.id.mapview);
	    mapView.setBuiltInZoomControls(true);
	    mapView.setBuiltInZoomControls(true);
	    
	    double lat = 41.8767689;
	    double longi = -87.6419204;
	
	    GeoP = new GeoPoint((int) (lat *1E6), (int) (longi *1E6));
	    
	    mControl = mapView.getController();
	    mControl.animateTo(GeoP);
	    mControl.setZoom(13);

	    MapOverlay touch = new MapOverlay();
	    overlayList = mapView.getOverlays();
	    overlayList.add(touch);
	    
	    drawPin = this.getResources().getDrawable(R.drawable.pin);

	}
//	
//	@Override
//	public void onSaveInstanceState(Bundle savedInstanceState) {
//	  super.onSaveInstanceState(savedInstanceState);
//	  // Save UI state changes to the savedInstanceState.
//	  // This bundle will be passed to onCreate if the process is
//	  // killed and restarted.
//	  savedInstanceState.putParcelable(key, value)(key, value)("overlayList", overlayList);
//	  savedInstanceState.putDouble("myDouble", 1.9);
//	  savedInstanceState.putInt("MyInt", 1);
//	  savedInstanceState.putString("MyString", "Welcome back to Android");
//	  // etc.
//	}
	
	
	
	@Override
	protected boolean isRouteDisplayed() {
	    return false;
	}

	public class MapOverlay extends Overlay {
		
		@SuppressWarnings("deprecation")
		final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
		    public void onLongPress(MotionEvent e) {
		        Log.e("", "Longpress detected");
		        x = (int) e.getX();
				y = (int) e.getY();
		        touchedPoint = mapView.getProjection().fromPixels(x, y);
		        
		        AlertDialog alert = new AlertDialog.Builder(LocationSelectionActivity.this).create();
				alert.setTitle("Pin");
				alert.setMessage("Do you want to pin this as this riddles location?");
				alert.setButton("Place pin", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						OverlayItem overlayItem = new OverlayItem(touchedPoint, "Location", getPinInfo());
						CustomPinpoint custom = new CustomPinpoint(drawPin, LocationSelectionActivity.this);
						custom.insertPinpoint(overlayItem);
						if(overlayList.size() == 2){
							overlayList.remove(1);
						}
						//overlayList.removeAll(CustomPinpoint);
						overlayList.add(custom);
						mapView.invalidate();
						
					}
				});

				alert.setButton2("Toggle View", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						
						if (mapView.isSatellite()){
							mapView.setSatellite(false);
							mapView.setStreetView(true);
						}
						else{
							mapView.setStreetView(false);
							mapView.setSatellite(true);
						}
						
					}
				});
				
				alert.setButton3("Cancel", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				alert.show();
				
		    }
		});

		public boolean onTouchEvent(MotionEvent event , MapView m) {
		    return gestureDetector.onTouchEvent(event);
		};

		private String getPinInfo(){
			String display = "";
			Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
			try{
				longitude = touchedPoint.getLatitudeE6() / 1E6;
				latitude = touchedPoint.getLongitudeE6() / 1E6;
				List<Address> address = geocoder.getFromLocation(longitude, latitude, 1);
				if (address.size() > 0){
					
					for (int i = 0; i < address.get(0).getMaxAddressLineIndex(); i++){
						display += address.get(0).getAddressLine(i) + "\n";
					}
					Toast t = Toast.makeText(getBaseContext(), "longi " + longitude + "lat " + latitude , Toast.LENGTH_LONG);
					t.show();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				
			}
			return display;
			
		}
		

	}

	public void onClick(View v) {
		switch(v.getId()) {
        case R.id.mapDone:
        	mapDone(v);
        	break;
		}
	}



	private void mapDone(View v) {
		if(overlayList.size() == 2){
			 Intent returnIntent = new Intent();
			 returnIntent.putExtra("resultLongitude", longitude);
			 returnIntent.putExtra("resultLatitude", latitude);
			 setResult(RESULT_OK,returnIntent);     
			 finish();
		}else{
			Intent returnIntent = new Intent();
			setResult(RESULT_CANCELED, returnIntent);        
			finish();
		}
	}
	
	@Override
	public void onBackPressed() {
		Intent returnIntent = new Intent();
		setResult(RESULT_CANCELED, returnIntent);        
		finish();
	    super.onBackPressed();
	}
}



