package com.example.android.mood.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.android.mood.R;

/**
 * Created by Joe on 4/4/18.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private int NUM_PAGES = 2;
    private Context context;

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new WeatherFragment();
            case 1:
                return new PoemFragment();
            default:
                return new EmptyFragment();
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.weather);
            case 1:
                return context.getString(R.string.poems);
            default:
                return null;
        }

    }
}
