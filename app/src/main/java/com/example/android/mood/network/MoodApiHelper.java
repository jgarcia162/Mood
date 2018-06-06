package com.example.android.mood.network;

import com.example.android.mood.model.poems.Poem;
import com.example.android.mood.model.weather.WeatherConstants;
import com.example.android.mood.model.weather.WeatherResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Joe on 4/3/18.
 */

//TODO RxJava to execute concurrent network calls

public class MoodApiHelper {

    public MoodApiHelper() {

    }

    public RxAerisService getRxAerisService() {
        return RetrofitFactory.getAerisRetrofitInstance().create(RxAerisService.class);
    }

    public Observable<WeatherResponse> getDayForecast(String city){
        return getRxAerisService().getDayForecast(city, WeatherConstants.ACCESS_ID,WeatherConstants.SECRET_KEY);
    }

    public Observable<WeatherResponse> getWeekForecast(String city){
        return getRxAerisService().getWeekForecast(city, WeatherConstants.ACCESS_ID,WeatherConstants.SECRET_KEY);
    }

    public RxPoetryService getRxPoetryService() {
        return RetrofitFactory.getPoetryRetrofitInstance().create(RxPoetryService.class);
    }

    public Observable<List<Poem>> getPoems(String author){
        return getRxPoetryService().getAuthorWorks(author);
    }

}