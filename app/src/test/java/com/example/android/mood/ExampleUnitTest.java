package com.example.android.mood;

import com.example.android.mood.model.poems.Poem;
import com.example.android.mood.network.PoetryService;
import com.example.android.mood.network.RetrofitFactory;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

//    @Test
//    public void weatherTest() {
//        Retrofit retrofit = RetrofitFactory.getAerisRetrofitInstance();
//        AerisService service = retrofit.create(AerisService.class);
//        Call<AerisResults> call = service.getWeekForecast("New York,NY", WeatherConstants.ACCESS_ID, WeatherConstants.SECRET_KEY);
//
//        call.enqueue(new Callback<AerisResults>() {
//            @Override
//            public void onResponse(Call<AerisResults> call, Response<AerisResults> response) {
//                assertNotEquals(null, response.body());
//                System.out.println(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<AerisResults> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }

    @Test
    public void poetryTest(){
        Retrofit retrofit = RetrofitFactory.getPoetryRetrofitInstance();
        PoetryService service = retrofit.create(PoetryService.class);
        Call<List<Poem>> call = service.getAuthorWorks("Emily Dickinson");
        call.enqueue(new Callback<List<Poem>>() {
            @Override
            public void onResponse(Call<List<Poem>> call, Response<List<Poem>> response) {
                Assert.assertNotEquals(null,response.body());
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<List<Poem>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}