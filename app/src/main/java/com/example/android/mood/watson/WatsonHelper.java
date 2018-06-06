package com.example.android.mood.watson;


import android.content.Context;

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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class WatsonHelper {
    private static ToneAnalyzer toneAnalyzer;
    private static WatsonHelper instance;
    public final static Set<String> possibleTones = new HashSet<>(Arrays.asList("anger", "disgust", "fear", "joy", "sadness", "analytical", "confident", "tentative"));


    public WatsonHelper() {
    }

    public static WatsonHelper getInstance() {
        if (instance == null) {
            instance = new WatsonHelper();
            toneAnalyzer = new ToneAnalyzer("2018-04-10");
//            toneAnalyzer = new ToneAnalyzer("2017-09-21");
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


            toneAnalyzer.setUsernameAndPassword(username, password);

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    public void getWeatherTone(String text, final WatsonListener listener) {
        ToneOptions options = new ToneOptions.Builder().html(text).build();

        toneAnalyzer.tone(options).enqueue(new ServiceCallback<ToneAnalysis>() {
            @Override
            public void onResponse(ToneAnalysis response) {
                DocumentAnalysis documentAnalysis = response.getDocumentTone();
                listener.onWeatherToneFetched(documentAnalysis.getTones().get(0).getToneName());

//                listener.onWeatherToneFetched(documentAnalysis.getToneCategories().get(0).getTones().get(0).getToneName());
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void getPoemTone(String text, final WatsonListener listener) {
        ToneOptions options = new ToneOptions.Builder().html(text).build();

        toneAnalyzer.tone(options).enqueue(new ServiceCallback<ToneAnalysis>() {
            @Override
            public void onResponse(ToneAnalysis response) {
                DocumentAnalysis documentAnalysis = response.getDocumentTone();
                if (documentAnalysis.getTones().size() != 0) {
                    listener.onPoemToneFetched(documentAnalysis.getTones().get(0).getToneId());
                }else {
                    listener.onPoemToneFetched(null);
                }
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Observable<String[]> poemObservable = Observable.just(WatsonHelper.getInstance().getTone(poemList.get(randomPoemIndex).getFullPoem()));
     * <p>
     * poemObservable.subscribe(new Observer<String[]>() {
     *
     * @Override public void onSubscribe(Disposable d) {
     * }
     * @Override public void onNext(String[] strings) {
     * poemList.get(randomPoemIndex).setMood(strings[0]);
     * Toast.makeText(context, "OBSERVABLE", Toast.LENGTH_SHORT).show();
     * <p>
     * }
     * @Override public void onError(Throwable e) {
     * <p>
     * }
     * @Override public void onComplete() {
     * <p>
     * }
     * }
     * );
     */

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
