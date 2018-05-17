package com.example.android.mood.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.mood.R;
import com.example.android.mood.model.WeatherPoetry;
import com.example.android.mood.model.aeris.AerisConstants;
import com.example.android.mood.model.aeris.AerisPeriod;
import com.example.android.mood.model.aeris.AerisResponse;
import com.example.android.mood.model.poetry.Poem;
import com.example.android.mood.network.MoodApiHelper;
import com.example.android.mood.network.RxJava2ApiCallback;
import com.example.android.mood.network.RxJavaCallHelper;
import com.example.android.mood.room.MoodDatabase;
import com.example.android.mood.watson.WatsonHelper;
import com.example.android.mood.watson.WatsonListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Joe on 4/3/18.
 */

public class WeatherFragment extends Fragment implements WatsonListener {
    private final String TAG = getClass().getCanonicalName();

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    private List<Poem> poemList;
    private List<AerisPeriod> weatherList = new ArrayList<>();
    private List<WeatherPoetry> dataSet = new ArrayList<>();
    private Random randomPoemIndexGenerator = new Random();
    private int randomPoemIndex;
    private MoodApiHelper moodApiHelper;
    private Gson gson;
    private MoodDatabase database;
    private Poem randomPoem;
    private List<WeatherPoetry> weatherPoetryDataSet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
        database = MoodDatabase.getInstance(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        database = MoodDatabase.getInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        moodApiHelper = new MoodApiHelper();

        Observable<AerisResponse> weatherObservable = getAerisObservable();

        Observable<List<Poem>> poemsObservable = getPoemsObservable();

        createWeatherDisposable(weatherObservable);
    }

    private void createWeatherDisposable(Observable<AerisResponse> observable) {

        Disposable disposable = RxJavaCallHelper.call(observable, new RxJava2ApiCallback<AerisResponse>() {
            @Override
            public void onSuccess(AerisResponse aerisResponse) {
                onForecastFetched(aerisResponse.getPeriods());
            }

            @Override
            public void onFailed(Throwable throwable) {
                Log.d(TAG, "onFailed() called with: throwable = [" + throwable + "]");
            }
        });
    }

    private void createPoemDisposable(Observable<List<Poem>> observable){

        Disposable disposable = RxJavaCallHelper.call(observable, new RxJava2ApiCallback<List<Poem>>() {
            @Override
            public void onSuccess(List<Poem> poems) {
                onPoemsFetched(poems);
            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });
    }


    private Observable<List<Poem>> getPoemsObservable() {

        return moodApiHelper
                .getRxPoetryService()
                .getAuthorWorks("Emily Dickinson");
    }

    private Observable<AerisResponse> getAerisObservable() {

        return moodApiHelper
                .getRxAerisService()
                .getAerisResponse("New York,NY", AerisConstants.ACCESS_ID, AerisConstants.SECRET_KEY);
    }

    public void onPoemsFetched(List<Poem> poems) {
        poemList = poems;
        WatsonHelper.getInstance().configureToneAnalyzer(getContext());
    }

    public void onForecastFetched(List<AerisPeriod> forecast) {
        weatherList = forecast;
        randomPoemIndex = randomPoemIndexGenerator.nextInt(poemList.size());
        Poem randomPoem = poemList.get(randomPoemIndex);
        WatsonHelper.getInstance().getTone(randomPoem.getFullPoem(), this);
    }

    @Override
    public void onTonesFetched(String tone) {
        //TODO run in background with RxJava
        poemList.get(randomPoemIndex).setMood(tone);
        onAllDataFetched();
    }

    public void onAllDataFetched() {
        dataSet = createAerisPoetryList();
        setUpRecyclerView(dataSet);

        Executor executor = Executors.newFixedThreadPool(2);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.weatherPoetryDao().insertAll(dataSet);
            }
        });
    }

    private List<WeatherPoetry> createAerisPoetryList() {
        //TODO avoid repeated poems
        for (int i = 0; i < weatherList.size(); i++) {
            String weatherJsonString = gson.toJson(weatherList.get(i));
            String poemJsonString = gson.toJson(poemList.get(randomPoemIndex));
            dataSet.add(new WeatherPoetry(weatherJsonString, poemJsonString));
            randomPoemIndex = randomPoemIndexGenerator.nextInt(poemList.size()) + 1;
        }

        return dataSet;
    }


    private void setUpRecyclerView(List<WeatherPoetry> dataSet) {
        weatherPoetryDataSet = dataSet;
        Thread thread = new Thread() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setHasFixedSize(false);
                        recyclerView.setAdapter(new WeatherAdapter(weatherPoetryDataSet, recyclerView));
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
            }
        };

        thread.run();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        database.destroyInstance();
    }


    void saveWeatherDataToRoom(List<WeatherPoetry> dataSet) {
        database.weatherPoetryDao().insertAll(dataSet);
    }


}
