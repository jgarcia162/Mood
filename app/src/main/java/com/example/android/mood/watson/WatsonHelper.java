package com.example.android.mood.watson;


import android.content.Context;
import android.util.Log;

import com.example.android.mood.R;
import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.DocumentAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static android.content.ContentValues.TAG;

public class WatsonHelper {
    private static ToneAnalyzer toneAnalyzer;
    private static WatsonHelper instance;

    public WatsonHelper() {
    }

    public static WatsonHelper getInstance() {
        if (instance == null) {
            instance = new WatsonHelper();
            toneAnalyzer = new ToneAnalyzer("2018-04-10");
        }
        return instance;
    }

    public void configureToneAnalyzer(Context context) {
        try {

            JSONObject credentialsJSON = new JSONObject(IOUtils.toString(context.getResources().openRawResource(R.raw.credentials), "UTF-8"));

            JSONArray credentialsArray = credentialsJSON.getJSONArray("tone_analyzer");

            JSONObject credentialsObject = (JSONObject) credentialsArray.get(0);

            JSONObject credentials = credentialsObject.getJSONObject("credentials");

            String username = credentials.getString("username");

            String password = credentials.getString("password");

            Log.d(TAG, "configureToneAnalyzer: " + username + " - " + password);

            toneAnalyzer.setUsernameAndPassword(username, password);

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    public void getTones(String text, WatsonListener listener) {
        ToneOptions options = new ToneOptions.Builder().html(text).build();

        toneAnalyzer.tone(options).enqueue(new ServiceCallback<ToneAnalysis>() {
            @Override
            public void onResponse(ToneAnalysis response) {
                DocumentAnalysis documentAnalysis = response.getDocumentTone();
                listener.onTonesFetched(documentAnalysis.getTones());
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });
    }


}
