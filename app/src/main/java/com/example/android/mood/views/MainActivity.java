package com.example.android.mood.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.android.mood.CustomRunnable;
import com.example.android.mood.R;
import com.example.android.mood.model.WeatherPoem;
import com.example.android.mood.model.poems.Poem;
import com.example.android.mood.model.weather.Weather;
import com.example.android.mood.model.weather.WeatherResponse;
import com.example.android.mood.network.MoodApiHelper;
import com.example.android.mood.room.MoodDatabase;
import com.example.android.mood.watson.WatsonHelper;
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

@SuppressLint("CheckResult")
public class MainActivity extends AppCompatActivity implements WatsonListener {
    private MoodDatabase database;
    private MoodApiHelper moodApiHelper;
    private CompositeDisposable compositeDisposable;
    private List<Poem> poemList = new ArrayList<>();
    private Weather weather;
    private WeatherPoem weatherPoem;
    private Poem randomPoem;
    private FragmentManager fragmentManager;
    private int randomPoemIndex;
    private View rootView;
    private PagerAdapter pagerAdapter;
    private Handler handler;

    @BindView(R.id.view_pager)
    public ViewPager viewPager;
    @BindView(R.id.tablayout)
    public TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootView = findViewById(R.id.activity_main_root);

        ButterKnife.bind(this);
        WatsonHelper.getInstance().configureToneAnalyzer(getApplicationContext());

        getDatabase();
        fragmentManager = getSupportFragmentManager();
        moodApiHelper = new MoodApiHelper();
        compositeDisposable = new CompositeDisposable();


        final Observable<Object> allDataObservable = createWeatherAndPoemObservables();

        new CustomRunnable() {
            @Override
            public void run() {
            }
        };
                subscribeDataObserver(allDataObservable);

//        moodApiHelper
//                .getRxAerisService()
//                .getWeekForecast("New York, NY", WeatherConstants.ACCESS_ID, WeatherConstants.SECRET_KEY);

        //TODO remove this threadpolicy
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    private void setUpViewPager() {


        pagerAdapter = new ViewPagerAdapter(fragmentManager, weather);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }

    private Observable<Object> createWeatherAndPoemObservables() {
        //TODO change hardcoded location to use coordinates of current location
        Observable<WeatherResponse> weatherObservable = getAerisObservable("New York,NY");
        //TODO change hardcoded author name
        Observable<List<Poem>> poemsObservable = getPoemsObservable("Edgar Allan Poe");
        return Observable.concat(weatherObservable, poemsObservable);
    }

    //TODO wrap these methods
    private Observable<WeatherResponse> getAerisObservable(String location) {
        return moodApiHelper.getWeekForecast(location);
    }

    private Observable<List<Poem>> getPoemsObservable(String author) {
        return moodApiHelper.getPoems(author);
    }


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
                        showErrorMessage();
                    }

                    @Override
                    public void onComplete() {
                        getRandomPoem();
                        allDataFetched();
                    }
                });
    }

    private void getRandomPoem() {
        randomPoemIndex = new Random().nextInt(poemList.size());
        randomPoem = poemList.get(randomPoemIndex);
    }

    private void allDataFetched() {
        WatsonHelper.getInstance().getWeatherTone(weather.getWeatherDescription(), this);
    }

    @Override
    public void onWeatherToneFetched(String tone) {
        //TODO run in background with RxJava
        weather.setTone(tone);
        WatsonHelper.getInstance().getPoemTone(randomPoem.getFullPoem(), this);
    }

    private void showErrorMessage() {
        Snackbar snackbar = Snackbar.make(rootView, "Something went wrong", BaseTransientBottomBar.LENGTH_LONG);
        snackbar.show();
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
    public void onPoemToneFetched(String tone) {
        weatherPoem = new WeatherPoem(weather, randomPoem);
        setUpViewPager();
        /*
        if (!weather.getTone().equals(tone)) {
            getRandomPoem();
            WatsonHelper.getInstance().getPoemTone(randomPoem.getFullPoem(), this);
        } else {
            weatherPoem = new WeatherPoem(weather, randomPoem);
            saveDataToRoom(weatherPoem);
            showWeatherFragment();
        }*/
    }

//    private void showWeatherFragment() {
//        WeatherFragment weatherFragment = new WeatherFragment();
//        Bundle bundle = new Bundle();
//
//        bundle.putParcelable("weather", weather);
//        weatherFragment.setArguments(bundle);
//        fragmentManager.beginTransaction().replace(R.id.fragment_container, weatherFragment, "weather_fragment").addToBackStack("weather_fragment").commit();
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        database.destroyInstance();
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}