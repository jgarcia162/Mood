package com.example.android.mood.network;

/**
 * Created by Joe on 4/3/18.
 */

//TODO RxJava to execute concurrent network calls

public class MoodApiHelper {

    public MoodApiHelper() {

    }

    public RxAerisService getRxAerisService(){
        return RetrofitFactory.getAerisRetrofitInstance().create(RxAerisService.class);
    }

    public RxPoetryService getRxPoetryService(){
        return RetrofitFactory.getPoetryRetrofitInstance().create(RxPoetryService.class);
    }

//    public Observable<List<Poem>> getPoems() {
//        Retrofit poemRetrofit = RetrofitFactory.getPoetryRetrofitInstance();
//        PoetryService poetryService = poemRetrofit.create(PoetryService.class);
//        //TODO get user's favorite author from prefs or random if none or not found
//        return poetryService.getAuthorWorks("Emily Dickinson");
//    }
}