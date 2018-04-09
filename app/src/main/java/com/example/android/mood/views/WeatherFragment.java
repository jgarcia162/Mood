package com.example.android.mood.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.mood.R;
import com.example.android.mood.model.WeatherPoetry;
import com.example.android.mood.model.aeris.AerisPeriod;
import com.example.android.mood.model.poetry.Poem;
import com.example.android.mood.network.DataFetcher;
import com.example.android.mood.network.DataListener;
import com.example.android.mood.room.MoodDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Joe on 4/3/18.
 */

public class WeatherFragment extends Fragment implements DataListener {
    private final String TAG = getClass().getCanonicalName();
    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    private List<Poem> poemList;
    private List<AerisPeriod> weatherList = new ArrayList<>();
    private List<WeatherPoetry> dataSet = new ArrayList<>();
    private Random randomPoemIndexGenerator = new Random();
    private int randomPoemIndex;
    private DataFetcher dataFetcher;
    private Context context;
    private Gson gson;
    private MoodDatabase database;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        gson = new Gson();
        database = MoodDatabase.getInstance(context);
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
        dataFetcher = new DataFetcher(this);
        dataFetcher.getPoems();
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
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new WeatherAdapter(dataSet, recyclerView));
    }

    @Override
    public void onForecastFetched(List<AerisPeriod> forecast) {
        weatherList = forecast;
        onAllDataFetched();
    }

    @Override
    public void onPoemsFetched(List<Poem> poems) {
        poemList = poems;
        randomPoemIndex = randomPoemIndexGenerator.nextInt(poemList.size()) + 1;
        dataFetcher.getForecast();
    }

    @Override
    public void onAllDataFetched() {
        setUpRecyclerView(createAerisPoetryList());
        //TODO run in background with RxJava
        Executor executor = Executors.newFixedThreadPool(2);
        executor.execute(() -> {
            database.weatherPoetryDao().insertAll(dataSet);

        });

    }


    void saveWeatherDataToRoom(List<WeatherPoetry> dataSet) {
        database.weatherPoetryDao().insertAll(dataSet);
    }
}
