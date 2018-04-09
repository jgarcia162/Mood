package com.example.android.mood.network;

import com.example.android.mood.model.aeris.AerisPeriod;
import com.example.android.mood.model.poetry.Poem;

import java.util.List;

/**
 * Created by Joe on 4/3/18.
 */

public interface DataListener {
    void onPoemsFetched(List<Poem> poems);
    void onForecastFetched(List<AerisPeriod> forecast);
    void onAllDataFetched();
}
