package com.richmond.riddler;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class LocationSelectionActivity extends MapActivity implements OnClickListener, GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener {

	private int x, y;
	private GeoPoint GeoP;
	private Button mapDoneButton; 
	private MapView mapView;
	private Drawable drawPin;
	private GeoPoint touchedPoint;
	private MapController mControl;
	private List<Overlay> overlayList;
	private double longitude=0, latitude=0;
	private LocationClient mLocationClient;
	private Location mCurrentLocation;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.activity_location_selection);
	    
	    mapView       = (MapView) findViewById(R.id.mapview);	    
        mapDoneButton = (Button)  findViewById(R.id.mapDone);

        mapDoneButton.setOnClickListener(this);
	    
	    mapView.setBuiltInZoomControls(true);
	    
	    mLocationClient = new LocationClient(this, this, this);
	    
//	    double lat = 41.8767689;
//	    double longi = -87.6419204;
	


	}
	
    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mLocationClient.connect();

    }
    
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onStop();
    }

	
	@Override
	protected boolean isRouteDisplayed() {
	    return false;
	}

	public class MapOverlay extends Overlay {
		
		final GestureDetector gestureDetector = new GestureDetector(getBaseContext(), new GestureDetector.SimpleOnGestureListener() {
		    public void onLongPress(MotionEvent e) {
		        Log.e("", "Longpress detected");
		        mControl.setZoom(25);
		        x = (int) e.getX();
				y = (int) e.getY();
		        touchedPoint = mapView.getProjection().fromPixels(x, y);
		        
		        AlertDialog alert = new AlertDialog.Builder(LocationSelectionActivity.this).create();
				alert.setTitle("Pin");
				alert.setMessage("Do you want to pin this as this riddles location?");
				alert.setButton(DialogInterface.BUTTON_POSITIVE, "Place pin", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						OverlayItem overlayItem = new OverlayItem(touchedPoint, "Location", getPinInfo());
						CustomPinpoint custom = new CustomPinpoint(drawPin, LocationSelectionActivity.this);
						custom.insertPinpoint(overlayItem);
						
						if(overlayList.size() == 2){
							overlayList.remove(1);
						}
						
						overlayList.add(custom);
						mapView.invalidate();
					}
				});

				alert.setButton(DialogInterface.BUTTON_NEUTRAL,"Toggle View", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						
						if (mapView.isSatellite()){
							mapView.setSatellite(false);
							//mapView.setStreetView(true);
						}
						else{
							//mapView.setStreetView(false);
							mapView.setSatellite(true);
						}
						
					}
				});
				
				alert.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel", new DialogInterface.OnClickListener() {					
					public void onClick(DialogInterface dialog, int which) {
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
				latitude = touchedPoint.getLatitudeE6() / 1E6;
				longitude = touchedPoint.getLongitudeE6() / 1E6;
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


	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (result.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
            	result.startResolutionForResult(
                        this,
                        0);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            //showErrorDialog(result.getErrorCode());
        	Toast.makeText(this, result.getErrorCode()+"", Toast.LENGTH_SHORT).show();
        }
		
	}


	@Override
	public void onConnected(Bundle connectionHint) {
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
	    mCurrentLocation = mLocationClient.getLastLocation();
	    GeoP = new GeoPoint((int) (mCurrentLocation.getLatitude() *1E6), (int) (mCurrentLocation.getLongitude() *1E6));
	    
	    mControl = mapView.getController();
	    mControl.animateTo(GeoP);
	    mControl.setZoom(17);

	    MapOverlay touch = new MapOverlay();
	    overlayList = mapView.getOverlays();
	    overlayList.add(touch);
	    
	    drawPin = this.getResources().getDrawable(R.drawable.pin);
		
	}


	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
		
	}
}



