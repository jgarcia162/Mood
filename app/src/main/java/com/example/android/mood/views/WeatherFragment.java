package com.example.android.mood.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.mood.R;
import com.example.android.mood.model.AerisPoetry;
import com.example.android.mood.model.aeris.AerisPeriod;
import com.example.android.mood.model.poetry.Poem;
import com.example.android.mood.network.DataFetcher;
import com.example.android.mood.network.DataListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Joe on 4/3/18.
 */

public class WeatherFragment extends Fragment implements DataListener{
    private final String TAG = getClass().getCanonicalName();
    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    private List<Poem> poemList;
    private List<AerisPeriod> weatherList = new ArrayList<>();
    private List<AerisPoetry> dataSet = new ArrayList<>();
    private Random randomPoemIndexGenerator = new Random();
    private int randomPoemIndex;
    private DataFetcher dataFetcher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        dataFetcher = new DataFetcher(this);
        dataFetcher.getPoems();
    }

    private List<AerisPoetry> createAerisPoetryList() {
        //TODO avoid repeated poems
        for (int i = 0; i < weatherList.size(); i++) {
            dataSet.add(new AerisPoetry(weatherList.get(i), poemList.get(randomPoemIndex)));
            randomPoemIndex = randomPoemIndexGenerator.nextInt(poemList.size()) + 1;
        }
        return dataSet;
    }


    private void setUpRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new PoetryAdapter(createAerisPoetryList()));
    }

    @Override
    public void onForecastFetched(List<AerisPeriod> forecast) {
        weatherList = forecast;
        setUpRecyclerView();
    }

    @Override
    public void onPoemsFetched(List<Poem> poems) {
        poemList = poems;
        randomPoemIndex = randomPoemIndexGenerator.nextInt(poemList.size()) + 1;
        dataFetcher.getForecast();
    }
}
