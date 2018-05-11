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

    public void getTone(String text, final WatsonListener listener) {
        Log.d(TAG, "getTone: ");
        ToneOptions options = new ToneOptions.Builder().html(text).build();

        toneAnalyzer.tone(options).enqueue(new ServiceCallback<ToneAnalysis>() {
            @Override
            public void onResponse(ToneAnalysis response) {
                DocumentAnalysis documentAnalysis = response.getDocumentTone();
                listener.onTonesFetched(documentAnalysis.getTones().get(0).getToneName());
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Observable<String[]> poemObservable = Observable.just(WatsonHelper.getInstance().getTone(poemList.get(randomPoemIndex).getFullPoem()));

     poemObservable.subscribe(new Observer<String[]>() {
    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(String[] strings) {
    poemList.get(randomPoemIndex).setMood(strings[0]);
    Toast.makeText(context, "OBSERVABLE", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
    }
     );*/

    public String getTone(String text) {
        ToneOptions options = new ToneOptions.Builder().html(text).build();

       final String[] tone = new String[1];

        toneAnalyzer.tone(options).enqueue(new ServiceCallback<ToneAnalysis>() {
            @Override
            public void onResponse(ToneAnalysis response) {
                DocumentAnalysis documentAnalysis = response.getDocumentTone();
                tone[0] = documentAnalysis.getTones().get(0).getToneName();

            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });

        return tone[0];
    }
}
