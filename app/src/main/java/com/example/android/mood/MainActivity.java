package com.example.android.mood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.mood.model.AerisPoetry;
import com.example.android.mood.model.aeris.AerisPeriod;
import com.example.android.mood.model.poetry.Poem;
import com.example.android.mood.network.DataFetcher;
import com.example.android.mood.network.DataListener;
import com.example.android.mood.views.PoetryAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements DataListener {
    private final String TAG = getClass().getCanonicalName();
    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    private List<Poem> poemList;
    private List<AerisPeriod> weatherList = new ArrayList<>();
    private List<AerisPoetry> dataSet = new ArrayList<>();
    private Random randomPoemIndexGenerator = new Random();
    private int randomPoemIndex;
    private DataFetcher dataFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
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

//TODO add viewpager for poems history and current weather data
//TODO SQL database
