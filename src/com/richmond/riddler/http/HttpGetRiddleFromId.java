package com.richmond.riddler.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.os.AsyncTask;

import com.richmond.riddler.RiddleSequence;
import com.richmond.riddler.Web;

public class HttpGetRiddleFromId extends AsyncTask<Void, Void, RiddleSequence> {
	String mRiddleID;
	RiddleSequence mSequence;
	HttpGet mGet;
	
	public HttpGetRiddleFromId(String riddleID){
		mRiddleID = riddleID;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

	}

	
	
	@Override
	protected RiddleSequence doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); // Timeout
		mGet = new HttpGet(Web.BASE_URL + Web.GETRIDDLE + mRiddleID);
		try {
			HttpResponse response = client.execute(mGet);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			StringBuilder builder = new StringBuilder();
			for (String line = null; (line = reader.readLine()) != null;) {
				builder.append(line).append("\n");
			}
			JSONTokener tokener = new JSONTokener(builder.toString());
			JSONObject info = new JSONObject(tokener);
			
			mSequence =new RiddleSequence(info);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			

		return mSequence;
	}

	@Override
	protected void onPostExecute(RiddleSequence result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}



	
	
	
}
