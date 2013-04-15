package com.richmond.riddler;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class HttpRequests extends AsyncTask<Void, Void, HttpResponse> {
	private HttpPost mPost = new HttpPost(Web.BASE_URL + Web.POST);

	public void createPostRequest(final String[] riddles,
			final double[] locations, final double distance) {
		JSONObject riddlesObj = new JSONObject();
		JSONObject hintsObj = new JSONObject();

		JSONObject locationOne = new JSONObject();
		JSONObject locationTwo = new JSONObject();
		JSONObject locationThree = new JSONObject();

		JSONObject JSONriddle = new JSONObject();

		try {

			// title.put(Web.RIDDLE_TITLE, riddles[6]);

			riddlesObj.put(Web.RIDDLE_ONE, riddles[0]);
			riddlesObj.put(Web.RIDDLE_TWO, riddles[1]);
			riddlesObj.put(Web.RIDDLE_THREE, riddles[2]);

			hintsObj.put(Web.RIDDLE_ONE_HINT, riddles[3]);
			hintsObj.put(Web.RIDDLE_TWO_HINT, riddles[4]);
			hintsObj.put(Web.RIDDLE_THREE_HINT, riddles[5]);

			locationOne.put(Web.LONG, locations[1]);
			locationOne.put(Web.LAT, locations[0]);
			locationTwo.put(Web.LONG, locations[3]);
			locationTwo.put(Web.LAT, locations[2]);
			locationThree.put(Web.LONG, locations[5]);
			locationThree.put(Web.LAT, locations[4]);

			JSONriddle.put(Web.RIDDLE_TITLE, riddles[6]);
			JSONriddle.put(new String(Web.RIDDLES), riddlesObj);
			JSONriddle.put(new String(Web.HINTS), hintsObj);
			JSONriddle.put(new String(Web.RIDDLE_ONE_LOCATION), locationOne);
			JSONriddle.put(new String(Web.RIDDLE_TWO_LOCATION), locationTwo);
			JSONriddle.put(new String(Web.RIDDLE_THREE_LOCATION), locationThree);
			JSONriddle.put(Web.DISTANCE, distance);
			Log.i("json", JSONriddle.toString());
			StringEntity se = new StringEntity(JSONriddle.toString());
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			mPost.setEntity(se);

		} catch (Exception e) {
			e.printStackTrace();
			// createDialog("Error", "Cannot Estabilish Connection");
		}
	}



	@Override
	protected HttpResponse doInBackground(Void... params) {
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(),
				10000); // Timeout Limit
		HttpResponse response = null;

		// JSONObject title = new JSONObject();

		try {
			response = client.execute(mPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

}
