package com.example.android.mood.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.mood.model.aeris.AerisConstants;
import com.example.android.mood.model.aeris.AerisPeriod;
import com.example.android.mood.model.aeris.AerisResponse;
import com.example.android.mood.model.poetry.Poem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;

/**
 * Created by Joe on 4/3/18.
 */

//TODO RxJava to execute concurrent network calls

public class DataFetcher {
    private DataListener dataListener;
    private List<Poem> poemsList;
    private List<AerisPeriod> weatherList;

    public DataFetcher(DataListener pListener) {
        dataListener = pListener;
    }

    public void getForecast() {
        Retrofit weatherRetrofit = RetrofitFactory.getAerisInstance();
        AerisService aerisService = weatherRetrofit.create(AerisService.class);
        //TODO get user location
        //shared pref or pref fragment for user location option, toggle on or off
        Call<AerisResponse> aerisResponseCall = aerisService.getResponse("New York,NY", AerisConstants.ACCESS_ID, AerisConstants.SECRET_KEY);
        aerisResponseCall.enqueue(new Callback<AerisResponse>() {
            @Override
            public void onResponse(@NonNull Call<AerisResponse> call, @Nullable Response<AerisResponse> response) {
                if (response != null) {
                    weatherList = response.body().getResults().get(0).getPeriods();
                    dataListener.onForecastFetched(weatherList);
                } else {
                    //TODO show error dialog
                }
            }

            @Override
            public void onFailure(@NonNull Call<AerisResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getPoems() {
        final Retrofit poemRetrofit = RetrofitFactory.getPoetryInstance();
        PoetryService poetryService = poemRetrofit.create(PoetryService.class);
        //TODO get user's favorite author from prefs or random if none or not found
        Call<List<Poem>> call = poetryService.getAuthorWorks("Emily Dickinson");
        Log.e(TAG, ""+call.request());
        call.enqueue(new Callback<List<Poem>>(){
            @Override
            public void onResponse(@NonNull Call<List<Poem>> call, @NonNull Response<List<Poem>> response) {
                poemsList = response.body();
                dataListener.onPoemsFetched(poemsList);
            }

            @Override
            public void onFailure(@NonNull Call<List<Poem>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}