package com.example.android.mood.views;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mood.R;
import com.example.android.mood.model.weather.Weather;
import com.example.android.mood.model.weather.WeatherResponse;
import com.example.android.mood.model.weather.WeatherUtils;
import com.example.android.mood.network.MoodApiHelper;
import com.qhutch.elevationimageview.ElevationImageView;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Joe on 4/3/18.
 */

public class WeatherFragment extends Fragment {

    private Weather weather;
    private List<Weather> weatherList;
    private Resources resources;

    @BindView(R.id.recyclerView)
    public RecyclerView weatherRecyclerView;

    @BindView(R.id.weather_date_tv)
    public TextView dayOfTheWeekTV;

    @BindView(R.id.temperature_tv)
    public TextView temperatureTV;

    @BindView(R.id.hi_low_tv)
    public TextView hiLowTV;

    @BindView(R.id.time_tv)
    public TextView timeTV;

    @BindView(R.id.weather_itemview_icon)
    public ElevationImageView elevationImageView;
    private MoodApiHelper moodApiHelper = new MoodApiHelper();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        weather = getArguments().getParcelable("weather");
    }


    private void setUpRecyclerView(List<Weather> weatherList) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        weatherRecyclerView.setLayoutManager(manager);
        weatherRecyclerView.setAdapter(new WeatherAdapter(weatherList));
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        dayOfTheWeekTV.setText(weather.getFullDayOfTheWeekName());
        timeTV.setText(weather.getDateTimeISO());
        temperatureTV.setText(String.valueOf(weather.getAvgTempF()));
        hiLowTV.setText(getResources().getString(R.string.hi_low, weather.getMaxTempF(), weather.getMinTempF()));


        String icon = weather.getIcon();
        Drawable iconDrawable = getDrawable(icon);

        elevationImageView.setImageDrawable(iconDrawable);
        elevationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViews();
            }
        });

        getWeekForecast();
    }




    private Drawable getDrawable(String icon) {
        resources = Objects.requireNonNull(getContext()).getResources();

        String uri = "@drawable/".concat(icon);
        uri = uri.replace(".png", "");

        int imageResource = resources.getIdentifier(uri, null, Objects.requireNonNull(getContext()).getPackageName());

        if (imageResource == 0) {
            imageResource = WeatherUtils.getAlternateResourceId(icon);
            return resources.getDrawable(imageResource, null);
        } else {
            return resources.getDrawable(imageResource, null);
        }
    }

    @SuppressLint("CheckResult")
    private void getWeekForecast() {
        getAerisObservable("New York, NY")
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<WeatherResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeatherResponse weatherResponse) {
                        weatherList = weatherResponse.getPeriods();
                        setUpRecyclerView(weatherList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private Observable<WeatherResponse> getAerisObservable(String location) {
        return moodApiHelper.getWeekForecast(location);
    }


    private void getLocation() {
        //TODO implement getLocation()
    }


    private void updateViews() {

    }

    @Override
    public void onDestroy() {
        resources = null;
        super.onDestroy();
    }


}
