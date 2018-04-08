package com.example.android.mood.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.android.mood.model.WeatherPoetry;

import java.util.List;

/**
 * Created by Joe on 4/7/18.
 */

@Dao
public interface WeatherPoetryDao {
    @Query("SELECT * FROM weatherpoetry")
    List<WeatherPoetry> getAll();

    @Insert
    void insert(WeatherPoetry... weatherPoetry);

    @Insert
    void insertAll(List<WeatherPoetry> weatherPoetryList);

    @Delete
    void delete(WeatherPoetry weatherPoetry);

}
