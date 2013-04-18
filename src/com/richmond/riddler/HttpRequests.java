package com.richmond.riddler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class HttpRequests extends AsyncTask<Void, Void, HttpResponse> {
	private HttpPut mPut;


	public void createAddPoints(int aPoints) {
		mPut = new HttpPut(Web.BASE_URL + Web.UPDATEUSER + AbstractGetNameTask.getmEmailAddress());
		JSONObject userObj = new JSONObject();
		JSONObject incObj = new JSONObject();
		
		try {
			userObj.put(Web.POINTS, aPoints);
			incObj.put(Web.INCPOINTS, userObj);
			
			StringEntity se = new StringEntity(incObj.toString());
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			mPut.setEntity(se);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



	@Override
	protected HttpResponse doInBackground(Void... params) {
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(),
				10000); // Timeout Limit
		HttpResponse response = null;

		try {
				response = client.execute(mPut);
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
