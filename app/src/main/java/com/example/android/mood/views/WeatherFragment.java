package com.example.android.mood.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.concurrent.ThreadLocalRandom;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Joe on 4/3/18.
 */

public class WeatherFragment extends Fragment implements WatsonListener {
    private final String TAG = getClass().getCanonicalName();

    //TODO change from list of weather to single weather item

    private List<Poem> poemList = new ArrayList<>();
    private Weather weather;
    private WeatherPoem weatherPoem;
    private Poem randomPoem;
    private int randomPoemIndex;
    private MoodApiHelper moodApiHelper;
    private MoodDatabase database;
    private CompositeDisposable compositeDisposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = MoodDatabase.getInstance(getContext());
        //TODO remove this
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    @Override
    public void onResume() {
        super.onResume();
        database = MoodDatabase.getInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        moodApiHelper = new MoodApiHelper();
        compositeDisposable = new CompositeDisposable();

        //TODO change hardcoded location to use coordinates of current location
        Observable<WeatherResponse> weatherObservable = getAerisObservable("New York,NY");
        //TODO change hardcoded author name
        Observable<List<Poem>> poemsObservable = getPoemsObservable("William Shakespeare");
        Observable<Object> allDataObservable = Observable.concat(weatherObservable, poemsObservable);

        subscribeDataObserver(allDataObservable);

    }

    private void getLocation() {
        //TODO implement getLocation()
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
                        randomPoemIndex = ThreadLocalRandom.current().nextInt(0, poemList.size());
                        randomPoem = poemList.get(randomPoemIndex);
                        weatherPoem = new WeatherPoem(weather, randomPoem);
                        saveDataToRoom(weatherPoem);
                        updateViews();
                    }
                });
    }

    private void updateViews() {

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

    private void saveDataToRoom(final WeatherPoem data) {
        new Thread(){
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
