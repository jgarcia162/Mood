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
    @Query("SELECT * FROM poems")
    List<WeatherPoetry> getAll();

    @Query("SELECT COUNT(*) FROM poems")
    int getTicketCount();

    @Insert
    void insert(WeatherPoetry... weatherPoetry);

    @Delete
    void delete(WeatherPoetry weatherPoetry);
}
