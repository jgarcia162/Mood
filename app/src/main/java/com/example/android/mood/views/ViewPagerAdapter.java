package com.example.android.mood.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.android.mood.model.weather.Weather;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private Weather weather;

    ViewPagerAdapter(FragmentManager fm, Weather weather) {
        super(fm);
        this.weather = weather;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                WeatherFragment weatherFragment = new WeatherFragment();
                bundle.putParcelable("weather", weather);
                weatherFragment.setArguments(bundle);
                return weatherFragment;
            case 1:
                return new PoemFragment();
            default:
                return new EmptyFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Weather";
            case 1:
                return "Poems";
            default:
                return null;
        }

    }

    public void setWeather(Weather newWeather){
        weather = newWeather;
        notifyDataSetChanged();
    }
}
