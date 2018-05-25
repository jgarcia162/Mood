package com.example.android.mood.network;

import com.example.android.mood.model.weather.WeatherResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RxAerisService {
    @GET("forecasts/{city}")
    Observable<WeatherResponse> getWeekForecast(@Path("city") String city, @Query("client_id") String clientId, @Query("client_secret") String clientSecret);

    @GET("/forecasts/minneapolis,mn?from=today&to=today&")
    Observable<WeatherResponse> getDayForecast(@Path("city") String city, @Query("client_id") String clientId, @Query("client_secret") String clientSecret);
}
