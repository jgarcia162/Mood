package com.example.android.mood.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Joe on 4/2/18.
 */

@Entity(tableName = "weatherpoetry")
public class WeatherPoetry {
    @PrimaryKey(autoGenerate = true)
    private int tid;

    @ColumnInfo(name = "weather")
    private String weather;

    @ColumnInfo(name = "poem")
    private String poem;

    public WeatherPoetry(String weather, String poem) {
        this.weather = weather;
        this.poem = poem;
    }

    public int getTid() {
        return tid;
    }

    public String getWeather() {
        return weather;
    }

    public String getPoem() {
        return poem;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }



}
