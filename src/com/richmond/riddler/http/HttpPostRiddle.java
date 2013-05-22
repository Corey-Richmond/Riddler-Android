package com.richmond.riddler.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.richmond.riddler.AbstractGetNameTask;
import com.richmond.riddler.RiddleSequence;
import com.richmond.riddler.User;
import com.richmond.riddler.Web;

public class HttpPostRiddle extends AsyncTask<Void, Void, HttpResponse> {
	private HttpPost mPost;
	private HttpPut mPut;
	private RiddleSequence mRiddle;
	private Activity mActivity; 
	private User mUser;

	public HttpPostRiddle(Activity activity, RiddleSequence riddles) {
		mActivity = activity;
		mRiddle = riddles;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mUser = new User();

		mPost = new HttpPost(Web.BASE_URL + Web.POSTRIDDLE);
		mPut = new HttpPut(Web.BASE_URL + Web.UPDATEUSER + Web.RIDDLESCREATED + "/" + AbstractGetNameTask.getmEmailAddress());
	
		StringEntity se;
		try {

			se = new StringEntity(mRiddle.toJSON().toString());
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			mPost.setEntity(se);
			

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected HttpResponse doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); // Timeout
																				// Limit
		HttpResponse response = null;

		JSONObject json = new JSONObject();
		StringEntity se;
		
		try {
			response = client.execute(mPost);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			StringBuilder builder = new StringBuilder();
			for (String line = null; (line = reader.readLine()) != null;) {
				builder.append(line).append("\n");
			}
			JSONTokener tokener = new JSONTokener(builder.toString());
			JSONObject info  = new JSONObject(tokener);

			json.put(Web.RIDDLESCREATED, info.getString("_id"));
			se = new StringEntity(json.toString());
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			mPut.setEntity(se);
			client.execute(mPut);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return response;
	}

	@Override
	protected void onPostExecute(HttpResponse result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result != null)
			mActivity.finish();
		else{
			Toast.makeText(mActivity.getApplicationContext(),"Failed" , Toast.LENGTH_LONG).show();
		}
	}

}
