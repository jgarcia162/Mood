package com.example.android.mood.network;

import com.example.android.mood.model.poetry.PoetryConstants;
import com.example.android.mood.model.aeris.AerisConstants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Joe on 4/2/18.
 */

public class RetrofitFactory {
    public static Retrofit aerisInstance;
    public static Retrofit poetryInstance;

    public static Retrofit getAerisInstance() {
        if (aerisInstance == null) {
            aerisInstance = new Retrofit.Builder().baseUrl(AerisConstants.BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return aerisInstance;
    }

    public static Retrofit getPoetryInstance() {
        if (poetryInstance == null) {
            poetryInstance = new Retrofit.Builder().baseUrl(PoetryConstants.BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return poetryInstance;
    }

}
