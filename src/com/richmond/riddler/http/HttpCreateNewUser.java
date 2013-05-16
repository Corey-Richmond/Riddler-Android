package com.richmond.riddler.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.richmond.riddler.AbstractGetNameTask;
import com.richmond.riddler.RiddleSequence;
import com.richmond.riddler.RiddlesStarted;
import com.richmond.riddler.User;
import com.richmond.riddler.Web;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

public class HttpCreateNewUser extends AsyncTask<String, Void, Void>{
	ProgressDialog mDialog;
	private Activity mActivity;
	private HttpPost mPost;
	private String mEmail;
	private JSONObject mJson;
	private JSONArray mJsonArray;
	private User mUser;

	public HttpCreateNewUser( Activity a, String email) {
		mActivity = a;
		mEmail = email;
		mJson = new JSONObject();
		mJsonArray = new JSONArray();
		mUser = new User();
		List<String> empty = new ArrayList<String>();
		List<RiddlesStarted> emptyStarted = new ArrayList<RiddlesStarted>();
		Log.i("mEmail", mEmail);
		try {
			mJson.put(Web.FIRSTNAME, AbstractGetNameTask.getmName());
			mJson.put(Web.USERNAME, mEmail);
			mJson.put(Web.POINTS, 0);
			mJson.put(Web.RIDDLESCREATED, mJsonArray);
			mJson.put(Web.RIDDLESSTARTED, mJsonArray);
			mJson.put(Web.RIDDLESSOLVEDWITHSKIP, mJsonArray);
			mJson.put(Web.RIDDLESSOLVEDWITHOUTSKIPS, mJsonArray);
			mUser.setFirstName(AbstractGetNameTask.getmName());
			mUser.setUserName(mEmail);
			mUser.setRiddlesCreated(empty);
			mUser.setRiddlesStarted(emptyStarted);
			mUser.setRiddlesSolvedWithSkip(empty);
			mUser.setRiddlesSolvedWithoutSkip(empty);
			mUser.setPoints(0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mDialog = ProgressDialog.show(mActivity, "", "Creating New User Please Wait ...",
				false, true);
		
	}
	
	
	@Override
	protected Void doInBackground(String... params) {
		// TODO Auto-generated method stub
		mPost = new HttpPost(Web.BASE_URL + Web.POSTUSER);

		StringEntity se;
		try {
			se = new StringEntity(mJson.toString());
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			mPost.setEntity(se);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); // Timeout


		try {
			 client.execute(mPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		mUser.saveUser();
		mDialog.dismiss();
	}



}
