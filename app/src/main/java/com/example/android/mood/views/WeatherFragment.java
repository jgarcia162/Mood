package com.example.android.mood.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
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
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Joe on 4/3/18.
 */

public class WeatherFragment extends Fragment implements WatsonListener {
    private final String TAG = getClass().getCanonicalName();

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    private List<Poem> poemList = new ArrayList<>();
    private List<AerisPeriod> weatherList = new ArrayList<>();
    private List<WeatherPoetry> dataSet = new ArrayList<>();
    private Random randomPoemIndexGenerator = new Random();
    private int randomPoemIndex;
    private MoodApiHelper moodApiHelper;
    private Gson gson;
    private MoodDatabase database;
    private Poem randomPoem;
    private List<WeatherPoetry> weatherPoetryDataSet;
    private CompositeDisposable compositeDisposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
        database = MoodDatabase.getInstance(getContext());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

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

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        moodApiHelper = new MoodApiHelper();
        compositeDisposable = new CompositeDisposable();


        Observable<AerisResponse> weatherObservable = getAerisObservable();
        Observable<List<Poem>> poemsObservable = getPoemsObservable();
        Observable<Object> allDataObservable = Observable.concat(weatherObservable, poemsObservable);

        subscribeDataObserver(allDataObservable);
    }

    @SuppressLint("CheckResult")
    private void subscribeDataObserver(Observable<Object> allDataObservable) {
        allDataObservable
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Object o) {

                        if (o instanceof AerisResponse) {
                            weatherList = ((AerisResponse) o).getPeriods();
                        } else {
                            poemList = (List<Poem>) o;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                        dataSet = createAerisPoetryList();
                        setUpRecyclerView(dataSet);
                    }
                });
    }


    private Observable<List<Poem>> getPoemsObservable() {
        //TODO change hardcoded author name
        return moodApiHelper
                .getRxPoetryService()
                .getAuthorWorks("Emily Dickinson");
    }


    private Observable<AerisResponse> getAerisObservable() {
        //TODO change hardcoded location
        return moodApiHelper
                .getRxAerisService()
                .getAerisResponse("New York,NY", AerisConstants.ACCESS_ID, AerisConstants.SECRET_KEY);
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
//        onAllDataFetched();
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


    private void setUpRecyclerView(List<WeatherPoetry> dataSet) {
        Log.d(TAG, "setUpRecyclerView: ");
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(new WeatherAdapter(dataSet, recyclerView));
        recyclerView.getAdapter().notifyDataSetChanged();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        database.destroyInstance();
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }


    void saveWeatherDataToRoom(List<WeatherPoetry> dataSet) {
        database.weatherPoetryDao().insertAll(dataSet);
    }


}
