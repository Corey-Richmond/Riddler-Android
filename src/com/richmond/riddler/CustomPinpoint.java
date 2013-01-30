package com.richmond.riddler;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class CustomPinpoint extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> pinpoints = new ArrayList<OverlayItem>();
	private Context c;

	public CustomPinpoint(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		// TODO Auto-generated constructor stub
	}

	public CustomPinpoint(Drawable m, Context context) {
		this(m);
		c = context;
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return pinpoints.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return pinpoints.size();
	}

	public void insertPinpoint(OverlayItem item) {
		pinpoints.add(item);
		this.populate();
	}

	@Override
	protected boolean onTap(int index) {
		// get the appropriate item
		OverlayItem item = pinpoints.get(index);
		// build an alert dialog to show to the user
		AlertDialog.Builder dialog = new AlertDialog.Builder(c);
		// add the overlay item's title and snippet
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		dialog.show();
		// I have consumed this click, don't pass on to other overlays
		return true;
	}
}