package com.example.android.mood;

import com.example.android.mood.model.poetry.Poem;
import com.example.android.mood.model.aeris.AerisConstants;
import com.example.android.mood.model.aeris.AerisResponse;
import com.example.android.mood.network.AerisService;
import com.example.android.mood.network.PoetryService;
import com.example.android.mood.network.RetrofitFactory;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static org.junit.Assert.assertNotEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void weatherTest() {
        Retrofit retrofit = RetrofitFactory.getAerisInstance();
        AerisService service = retrofit.create(AerisService.class);
        Call<AerisResponse> call = service.getResponse("New York,NY", AerisConstants.ACCESS_ID, AerisConstants.SECRET_KEY);

        call.enqueue(new Callback<AerisResponse>() {
            @Override
            public void onResponse(Call<AerisResponse> call, Response<AerisResponse> response) {
                assertNotEquals(null, response.body());
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<AerisResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Test
    public void poetryTest(){
        Retrofit retrofit = RetrofitFactory.getPoetryInstance();
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