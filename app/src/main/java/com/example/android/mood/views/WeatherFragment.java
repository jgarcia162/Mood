package com.example.android.mood.views;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mood.R;
import com.example.android.mood.model.weather.Weather;
import com.example.android.mood.model.weather.WeatherConstants;
import com.qhutch.elevationimageview.ElevationImageView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Joe on 4/3/18.
 */

public class WeatherFragment extends Fragment {
    private Weather weather;
    @BindView(R.id.weather_date_tv)
    public TextView dayOfTheWeekTV;
    @BindView(R.id.weather_tv)
    public TextView weatherTV;
    @BindView(R.id.weather_itemview_icon)
    public ElevationImageView elevationImageView;
    private final String TAG = getClass().getCanonicalName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        weather = getArguments().getParcelable("weather");
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

        dayOfTheWeekTV.setText(weather.getFullDayOfTheWeekName());
        weatherTV.setText(getResources().getString(R.string.weather_title, weather.getWeatherPrimary(), weather.getMaxTempF(), weather.getMinTempF()));
        Drawable weatherDrawable = WeatherConstants.getIconDrawable(Objects.requireNonNull(getContext()), weather.getIcon());

        if (weatherDrawable == null) {
            elevationImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.na, null));
        } else {
            elevationImageView.setImageDrawable(weatherDrawable);
            elevationImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateViews();
                }
            });
        }

    }


    private void getLocation() {
        //TODO implement getLocation()
    }


    private void updateViews() {

    }
}
