package com.example.android.mood.network;

import com.example.android.mood.model.poems.Poem;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RxPoetryService {

    @GET("author/{author}")
    Observable<List<Poem>> getAuthorWorks(@Path("author")String author);

}
