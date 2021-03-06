package com.example.android.mood.views;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.mood.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 2;
    @BindView(R.id.view_pager)
    public ViewPager viewPager;
    @BindView(R.id.tablayout)
    public TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        viewPager.setAdapter(new ViewPagerAdapter(this, getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }
}