package com.example.android.mood.watson;

public interface WatsonListener {
    void onWeatherToneFetched(String tone);
    void onPoemToneFetched(String tone);
}
