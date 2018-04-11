package com.example.android.mood.watson;

import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;

import java.util.List;

public interface WatsonListener {
    void onTonesFetched(List<ToneScore> tonesScoreList);
}
