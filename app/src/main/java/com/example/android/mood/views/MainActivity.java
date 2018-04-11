package com.example.android.mood.views;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.mood.R;
import com.example.android.mood.watson.WatsonHelper;
import com.example.android.mood.watson.WatsonListener;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements WatsonListener {
    private static final int NUM_PAGES = 2;
    @BindView(R.id.view_pager)
    public ViewPager viewPager;
    @BindView(R.id.tablayout)
    public TabLayout tabLayout;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        pagerAdapter = new ViewPagerAdapter(this,getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        testWatson();
    }

    public void testWatson(){
        WatsonHelper helper = WatsonHelper.getInstance();

        helper.configureToneAnalyzer(this);

        ToneOptions options = new ToneOptions.Builder().html(getTestPoem()).build();

        helper.getTones(options, MainActivity.this);
    }

    public String getTestPoem() {
        return "Once upon a midnight dreary, while I pondered, weak and weary,Over many a quaint and curious volume of forgotten lore— While I nodded, nearly napping, suddenly there came a tapping,As of some one gently rapping, rapping at my chamber door. Tis some visitor, I muttered, “tapping at my chamber door— Only this and nothing more.";


    }

    @Override
    public void onTonesFetched(List<ToneScore> tonesScoreList) {
        Log.d("TONE ", "onTonesFetched: " + tonesScoreList.get(0).getToneName());
//        Toast.makeText(getApplicationContext(), tonesScoreList.get(0).getToneName(), Toast.LENGTH_SHORT).show();
    }
}