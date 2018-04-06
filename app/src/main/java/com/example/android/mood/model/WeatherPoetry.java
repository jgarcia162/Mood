package com.example.android.mood.model;

/**
 * Created by Joe on 4/2/18.
 */

public class WeatherPoetry {
    private Object weather;
    private Object poem;

    public WeatherPoetry(Object weatherObject, Object poemObject) {
        this.weather = weatherObject;
        this.poem = poemObject;
    }

    public Object getWeather() {
        return weather;
    }

    public Object getPoem() {
        return poem;
    }
}
