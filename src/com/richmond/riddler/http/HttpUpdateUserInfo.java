package com.richmond.riddler.http;

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
import android.util.Log;

import com.richmond.riddler.RiddlesStarted;
import com.richmond.riddler.Web;

public class HttpUpdateUserInfo extends AsyncTask<Void, Void, Void> {
	private String mId;
	private int mCurrentRiddle;
	private boolean mHint;
	private boolean mSkip;
	private String mUrl;
	private int mSwitchNum;
	private HttpPut mPut;
	private RiddlesStarted mRiddleStarted;

	public HttpUpdateUserInfo(RiddlesStarted started, String url, int switchNum) {
//		mId = Id;
//		mCurrentRiddle = currentRiddle;
//		mHint = hint;
//		mSkip = skip;
		mRiddleStarted = started;
		mUrl = url;
		mSwitchNum = switchNum;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mPut = new HttpPut(mUrl);
		JSONObject json = new JSONObject();
		JSONObject info = new JSONObject();
		try {
			switch (mSwitchNum) {
			case 0:
				info.put(Web.ID, mRiddleStarted.getId());
				info.put(Web.CURRENTRIDDLE, mRiddleStarted.getCurrentRiddle());
				info.put(Web.HINTS, mRiddleStarted.isHint());
				info.put(Web.SKIPS, mRiddleStarted.isSkip());
				info.put(Web.FINISHEDWITHSKIP, mRiddleStarted.isFinishedWithSkip());
				info.put(Web.FINISHEDWITHOUTSKIP, mRiddleStarted.isFinishedWithoutSkip());
				Log.i("JSON", json.toString());
				json.put(Web.RIDDLESSTARTED, info );
				StringEntity se = new StringEntity(json.toString());
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				mPut.setEntity(se);
				break;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected Void doInBackground(Void... params) {
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(),
				10000); // Timeout Limit
		HttpResponse response = null;

		try {
				response = client.execute(mPut);
				Log.i("STARTED RIDDLE" , "*******START*********");
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
	}

}
