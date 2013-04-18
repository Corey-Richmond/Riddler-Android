package com.richmond.riddler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.os.AsyncTask;
import android.util.Log;

public class HttpGetList extends AsyncTask<Long, Void, List<RiddleSequence>> {

	@Override
	protected List<RiddleSequence> doInBackground(Long... params) {
		List<RiddleSequence> riddleSequences = new ArrayList<RiddleSequence>();

		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); // Timeout
																				// Limit
		HttpResponse response;
		try {
			HttpGet get;
			if (params[0] > 0)
				get = new HttpGet(Web.BASE_URL + Web.GETRIDDLE
						+ params[0].toString());
			else
				get = new HttpGet(Web.BASE_URL + Web.GETRIDDLES);
			response = client.execute(get);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			StringBuilder builder = new StringBuilder();
			for (String line = null; (line = reader.readLine()) != null;) {
				builder.append(line).append("\n");
			}
			JSONTokener tokener = new JSONTokener(builder.toString());
			JSONArray finalResult = new JSONArray(tokener);
			Log.i("Sfsgsdfg", finalResult.toString());
			for (int i = 0; i < finalResult.length(); i++) {

				JSONObject info = finalResult.getJSONObject(i);

				JSONObject riddles = info.getJSONObject(Web.RIDDLES);
				JSONObject hints = info.getJSONObject(Web.HINTS);
				JSONObject locationOne = info
						.getJSONObject(Web.RIDDLE_ONE_LOCATION);
				JSONObject locationTwo = info
						.getJSONObject(Web.RIDDLE_TWO_LOCATION);
				JSONObject locationThree = info
						.getJSONObject(Web.RIDDLE_THREE_LOCATION);

				RiddleSequence sequence = new RiddleSequence(
						info.getString("_id"),
						info.getString(Web.RIDDLE_TITLE),
						riddles.getString(Web.RIDDLE_ONE),
						riddles.getString(Web.RIDDLE_TWO),
						riddles.getString(Web.RIDDLE_THREE),
						hints.getString(Web.RIDDLE_ONE_HINT),
						hints.getString(Web.RIDDLE_TWO_HINT),
						hints.getString(Web.RIDDLE_THREE_HINT),
						locationOne.getDouble(Web.LONG),
						locationOne.getDouble(Web.LAT),
						locationTwo.getDouble(Web.LONG),
						locationTwo.getDouble(Web.LAT),
						locationThree.getDouble(Web.LONG),
						locationThree.getDouble(Web.LAT),
						info.getDouble(Web.DISTANCE));

				Log.i("Sequence", sequence.toString());

				riddleSequences.add(sequence);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return riddleSequences;

	}
}
