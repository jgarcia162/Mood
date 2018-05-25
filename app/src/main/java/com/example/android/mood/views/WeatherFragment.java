package com.example.android.mood.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mood.R;
import com.example.android.mood.model.weather.Weather;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Joe on 4/3/18.
 */

public class WeatherFragment extends Fragment {
    private Weather weather;
    @BindView(R.id.weather_date_tv)
    public TextView dayOfTheWeekTV;
    private final String TAG = getClass().getCanonicalName();

    //TODO change from list of weather to single weather item

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        weather = getArguments().getParcelable("weather");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_item_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //TODO Bind views
        dayOfTheWeekTV.setText(weather.getFullDayOfTheWeekName());


    }

    private void getLocation() {
        //TODO implement getLocation()
    }


    private void updateViews() {

    }
}
