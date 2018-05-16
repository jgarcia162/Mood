package com.example.android.mood.network;

import com.example.android.mood.model.poetry.Poem;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Joe on 4/2/18.
 */

public interface PoetryService {
    //Gets all the authors in the API DB
//    @GET("author")
//    Call<List<String>> getAuthors();

    /**
    * Getting the authors using RxJava requires an Observable return type */
    @GET("author")
    Observable<List<String>> getAuthors();

    @GET("author/{author}")
    Call<List<Poem>> getAuthorWorks(@Path("author")String author);

//    @GET("author/{author}")
//    Observable<List<Poem>> getAuthorWorks(@Path("author")String author);

    //Gets titles of all the works of the author
//    @GET("author/{author}/title")
//    Call<List<String>> getTitles(@Path("author") String author);

    @GET("author/{author}/title")
    Observable<List<String>> getTitles(@Path("author") String author);
}
