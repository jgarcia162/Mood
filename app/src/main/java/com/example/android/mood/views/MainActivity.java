package com.example.android.mood.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.mood.R;
import com.example.android.mood.model.WeatherPoem;
import com.example.android.mood.model.poems.Poem;
import com.example.android.mood.model.weather.Weather;
import com.example.android.mood.model.weather.WeatherConstants;
import com.example.android.mood.model.weather.WeatherResponse;
import com.example.android.mood.network.MoodApiHelper;
import com.example.android.mood.room.MoodDatabase;
import com.example.android.mood.watson.WatsonListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements WatsonListener{
    private MoodDatabase database;
    private MoodApiHelper moodApiHelper;
    private CompositeDisposable compositeDisposable;
    private List<Poem> poemList = new ArrayList<>();
    private Weather weather;
    private WeatherPoem weatherPoem;
    private Poem randomPoem;
    private FragmentManager fragmentManager;
    private int randomPoemIndex;

    private static final int NUM_PAGES = 2;
    @BindView(R.id.bottom_navigation)
    public BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getDatabase();

        fragmentManager = getSupportFragmentManager();

        moodApiHelper = new MoodApiHelper();
        compositeDisposable = new CompositeDisposable();

        Observable<Object> allDataObservable = createWeatherAndPoemObservables();
        subscribeDataObserver(allDataObservable);

        //TODO remove this threadpolicy
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_weather_item:
                               showWeatherFragment();
                                break;
                            case R.id.menu_poems_item:
                                fragmentManager.beginTransaction().replace(R.id.fragment_container,new PoemFragment(),"poem_fragment").addToBackStack("poem_fragment").commit();
                        }
                        return true;
                    }
                });
    }


    private Observable<Object> createWeatherAndPoemObservables() {
        //TODO change hardcoded location to use coordinates of current location
        Observable<WeatherResponse> weatherObservable = getAerisObservable("New York,NY");
        //TODO change hardcoded author name
        Observable<List<Poem>> poemsObservable = getPoemsObservable("William Shakespeare");
        return Observable.concat(weatherObservable, poemsObservable);
    }

    private Observable<List<Poem>> getPoemsObservable(String author) {
        return moodApiHelper
                .getRxPoetryService()
                .getAuthorWorks(author);
    }


    private Observable<WeatherResponse> getAerisObservable(String location) {
        return moodApiHelper
                .getRxAerisService()
                .getDayForecast(location, WeatherConstants.ACCESS_ID, WeatherConstants.SECRET_KEY);
    }

    @SuppressLint("CheckResult")
    private void subscribeDataObserver(Observable<Object> allDataObservable) {
        allDataObservable
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Object o) {
                        //TODO change WeatherPoem object in RxJava callback
                        if (o instanceof WeatherResponse) {

                            weather = ((WeatherResponse) o).getPeriods().get(0);

                        } else {
                            poemList = (List<Poem>) o;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        //TODO match weather to a poem
                        randomPoemIndex = new Random().nextInt(poemList.size()+1);
                        randomPoem = poemList.get(randomPoemIndex);
                        weatherPoem = new WeatherPoem(weather, randomPoem);
                        saveDataToRoom(weatherPoem);
                        showWeatherFragment();
                    }
                });
    }

    private void showWeatherFragment() {
        WeatherFragment weatherFragment = new WeatherFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelable("weather",weather);
        weatherFragment.setArguments(bundle);

        fragmentManager.beginTransaction().replace(R.id.fragment_container,weatherFragment,"weather_fragment").addToBackStack("weather_fragment").commit();
    }

    private void saveDataToRoom(final WeatherPoem data) {
        new Thread() {
            @Override
            public void run() {
                database.weatherPoemDao().insert(data);
//                if (database.weatherPoemDao().findByTitleAndAuthor(data.getPoemTitle(), data.getAuthor()) == null) {
//                    new Thread() {
//                        @Override
//                        public void run() {
//                        }
//                    }.start();
//                }
            }
        }.start();

    }

    @Override
    public void onResume() {
        super.onResume();
        getDatabase();
    }

    private void getDatabase() {
        if (database == null) {
            database = MoodDatabase.getInstance(getApplicationContext());
        }
    }

    @Override
    public void onTonesFetched(String tone) {
        //TODO run in background with RxJava
        poemList.get(randomPoemIndex).setMood(tone);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        database.destroyInstance();
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}