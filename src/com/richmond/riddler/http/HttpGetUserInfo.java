package com.richmond.riddler.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.richmond.riddler.User;
import com.richmond.riddler.Web;

public class HttpGetUserInfo extends AsyncTask<String, Void, Void> {

	public User getmUser() {
		return mUser;
	}


	private User mUser;
	private String mEmail;
	private Activity activity;
	private ProgressDialog mDialog;
	private HttpResponse response;

	public HttpGetUserInfo(Activity a) {
		activity = a;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mUser = new User();
		mDialog = ProgressDialog.show(activity, "", "Loading Please Wait ...",
				false, true);
	}

	@Override
	protected Void doInBackground(String... params) {

		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); // Timeout
																				// Limit
		try {
			HttpGet get;
			mEmail = params[0].toString();
			get = new HttpGet(Web.BASE_URL + Web.GETUSER + mEmail);
			response = client.execute(get);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			StringBuilder builder = new StringBuilder();
			for (String line = null; (line = reader.readLine()) != null;) {
				builder.append(line).append("\n");
			}
			JSONTokener tokener = new JSONTokener(builder.toString());
			JSONObject info = new JSONObject(tokener);
			
			Log.i("JSON", info.toString());
			Log.i("JSON Array", info.getJSONArray(Web.RIDDLESCREATED).toString());
			mUser.setFirstName(info.getString(Web.FIRSTNAME));
			mUser.setUserName(info.getString(Web.USERNAME));
			mUser.setRiddlesCreatedFromJSON(info.getJSONArray(Web.RIDDLESCREATED));
			mUser.setRiddlesStartedFromJSON(info.getJSONArray(Web.RIDDLESSTARTED));
			mUser.setRiddlesSolvedWithSkipFromJSON(info.getJSONArray(Web.RIDDLESSOLVEDWITHSKIP));
			mUser.setRiddlesSolvedWithoutSkipCreatedFromJSON(info.getJSONArray(Web.RIDDLESSOLVEDWITHOUTSKIPS));
			mUser.setPoints(info.getInt(Web.POINTS));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		if (mUser.getUserName() == null){
			mDialog.dismiss();
			Log.i("mEmail", mEmail);
			HttpCreateNewUser newUser = new HttpCreateNewUser(activity, mEmail);
			newUser.execute(mEmail);
		}
		
    	mUser.saveUser();
			
		mDialog.dismiss();

	}



}
