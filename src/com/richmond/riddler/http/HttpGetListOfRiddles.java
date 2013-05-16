package com.richmond.riddler.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
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
import android.util.JsonReader;

import com.richmond.riddler.RiddleSequence;
import com.richmond.riddler.Web;

public class HttpGetListOfRiddles extends AsyncTask<Void, Void, List<RiddleSequence>> {

	
	@Override
	protected List<RiddleSequence> doInBackground(Void... params) {
		List<RiddleSequence> riddleSequences = new ArrayList<RiddleSequence>();

		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); // Timeout
																				// Limit
		HttpResponse response;
		try {
			HttpGet get = new HttpGet(Web.BASE_URL + Web.GETRIDDLES);
			response = client.execute(get);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			StringBuilder builder = new StringBuilder();
			for (String line = null; (line = reader.readLine()) != null;) {
				builder.append(line).append("\n");
			}
			JSONTokener tokener = new JSONTokener(builder.toString());
			JSONArray finalResult = new JSONArray(tokener);

			
			for (int i = 0; i < finalResult.length(); i++) {
				JSONObject info = finalResult.getJSONObject(i);		
				RiddleSequence sequence = new RiddleSequence(info);
				//sequence = sequence.JSONToRiddleSequence(info);
				riddleSequences.add(sequence);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return riddleSequences;

	}
}
