package com.example.android.mood;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.mood.model.AerisPoetry;
import com.example.android.mood.model.aeris.AerisConstants;
import com.example.android.mood.model.aeris.AerisPeriod;
import com.example.android.mood.model.aeris.AerisResponse;
import com.example.android.mood.model.poetry.Poem;
import com.example.android.mood.network.AerisService;
import com.example.android.mood.network.PoetryService;
import com.example.android.mood.network.RetrofitFactory;
import com.example.android.mood.views.PoetryAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getCanonicalName();
    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    private List<Poem> poemList;
    private List<AerisPeriod> weatherList = new ArrayList<>();
    private List<AerisPoetry> dataSet = new ArrayList<>();
    private Random randomPoemIndexGenerator = new Random();
    private int randomPoemIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getPoems();
    }

    private void getPoems() {
        Retrofit poemRetrofit = RetrofitFactory.getPoetryInstance();
        PoetryService poetryService = poemRetrofit.create(PoetryService.class);
        Call<List<Poem>> call = poetryService.getAuthorWorks("Emily Dickinson");
        Log.d(TAG, "getPoems: " + call.request());
        call.enqueue(new Callback<List<Poem>>() {
            @Override
            public void onResponse(@NonNull Call<List<Poem>> call, @NonNull Response<List<Poem>> response) {
                poemList = response.body();
                assert poemList != null;
                Log.d(TAG, "onResponse: " + poemList.size());
                randomPoemIndex = randomPoemIndexGenerator.nextInt(poemList.size()) + 1;
                Log.d(TAG, "onResponse: " + randomPoemIndex);
                getWeather();
            }

            @Override
            public void onFailure(@NonNull Call<List<Poem>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getWeather() {
        Retrofit weatherRetrofit = RetrofitFactory.getAerisInstance();
        AerisService aerisService = weatherRetrofit.create(AerisService.class);
        Call<AerisResponse> aerisResponseCall = aerisService.getResponse("New York,NY", AerisConstants.ACCESS_ID, AerisConstants.SECRET_KEY);
        aerisResponseCall.enqueue(new Callback<AerisResponse>() {
            @Override
            public void onResponse(@NonNull Call<AerisResponse> call, @Nullable Response<AerisResponse> response) {
                assert response != null;
                weatherList = response.body().getResults().get(0).getPeriods();
                setUpRecyclerView();
            }

            @Override
            public void onFailure(@NonNull Call<AerisResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
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
}

//TODO add viewpager for poems history and current weather data
//TODO SQL database
