package com.example.android.mood.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Joe on 4/2/18.
 */

@Entity(tableName = "poems")
public class WeatherPoetry {
    @ColumnInfo(name = "weather")
    private Object weather;
    @ColumnInfo(name = "poem")
    private Object poem;

    public WeatherPoetry(Object weatherObject, Object poemObject) {
        this.weather = weatherObject;
        this.poem = poemObject;
    }

    @PrimaryKey(autoGenerate = true)
    private int tid;

    public Object getWeather() {
        return weather;
    }

    public Object getPoem() {
        return poem;
    }
}
