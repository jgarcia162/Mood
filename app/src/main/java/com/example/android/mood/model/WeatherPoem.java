package com.example.android.mood.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.android.mood.model.poems.Poem;
import com.example.android.mood.model.weather.Weather;

/**
 * Created by Joe on 4/2/18.
 */

@Entity(tableName = "weatherpoetry")
public class WeatherPoem {

    @PrimaryKey(autoGenerate = true)
    private int tid;

    @ColumnInfo(name = "weather")
    private String weather;

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @ColumnInfo(name = "poem")
    private String poem;

    @ColumnInfo(name = "poem_title")
    private String poemTitle;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "mood")
    private String mood;


    public WeatherPoem(){

    }

    public WeatherPoem(Weather weatherObject, Poem poemObject) {
        this.weather = weatherObject.getWeatherPrimary();
        this.poem = poemObject.getFullPoem();
        this.poemTitle = poemObject.getTitle();
        this.author = poemObject.getAuthor();
        this.mood = poemObject.getMood();
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getPoemTitle() {
        return poemTitle;
    }

    public void setPoemTitle(String poemTitle) {
        this.poemTitle = poemTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public void setPoem(String poem) {
        this.poem = poem;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }


}
