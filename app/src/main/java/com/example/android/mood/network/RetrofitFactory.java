package com.example.android.mood.network;

import com.example.android.mood.model.aeris.AerisConstants;
import com.example.android.mood.model.poetry.PoetryConstants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Joe on 4/2/18.
 */

public class RetrofitFactory {
    public static Retrofit aerisInstance;
    public static Retrofit poetryInstance;

    public static Retrofit getAerisRetrofitInstance() {
        if (aerisInstance == null) {

            aerisInstance = new Retrofit.Builder()
                    .baseUrl(AerisConstants.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory
                            .create())
                    .addConverterFactory(GsonConverterFactory
                            .create())
                    .build();
        }

        return aerisInstance;
    }

    public <S> S getAerisService(Class<S> serviceClass) {
        return getAerisRetrofitInstance().create(serviceClass);
    }

    public static Retrofit getPoetryRetrofitInstance() {
        if (poetryInstance == null) {


            Retrofit.Builder builder = new Retrofit.Builder();

            poetryInstance = builder.baseUrl(PoetryConstants.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory
                            .create())
                    .addConverterFactory(GsonConverterFactory
                            .create())
                    .client(createOkHttpClient())
                    .build();

        }

        return poetryInstance;
    }

    public <S> S getPoetryService(Class<S> serviceClass) {
        return getAerisRetrofitInstance().create(serviceClass);
    }

    /**
     * These createOkHttpClient() and createOkHttpInterceptor() create an HttpLoggingInterceptor to track API requests for debugging
     */
    private static OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder().addInterceptor(createOkHttpIntereceptor()).build();
    }

    private static HttpLoggingInterceptor createOkHttpIntereceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

}
