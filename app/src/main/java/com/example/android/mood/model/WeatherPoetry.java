package com.example.android.mood.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Joe on 4/2/18.
 */

@Entity(tableName = "weatherpoetry")
public class WeatherPoetry {

    @ColumnInfo(name = "weather")
    private String weather;
    @ColumnInfo(name = "poem")
    private String poem;

    public WeatherPoetry(String weather, String poem) {
        this.weather = weather;
        this.poem = poem;
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
