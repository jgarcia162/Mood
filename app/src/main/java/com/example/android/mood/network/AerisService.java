package com.example.android.mood.network;

import com.example.android.mood.model.aeris.AerisResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Joe on 4/2/18.
 */

public interface AerisService {
    @GET("forecasts/{city}")
    Call<AerisResponse> getResponse(@Path("city") String city, @Query("client_id") String clientId, @Query("client_secret") String clientSecret);
}
