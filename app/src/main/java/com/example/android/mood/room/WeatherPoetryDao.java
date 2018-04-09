package com.example.android.mood.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.example.android.mood.model.WeatherPoetry;

import java.util.List;

/**
 * Created by Joe on 4/7/18.
 */

@Dao
public abstract class WeatherPoetryDao {
    @Query("SELECT * FROM weatherpoetry")
    public abstract List<WeatherPoetry> getAll();

    @Insert
    public abstract void insert(WeatherPoetry... weatherPoetry);

    @Insert
    public abstract void insertAll(List<WeatherPoetry> weatherPoetryList);

    @Delete
    public abstract void delete(WeatherPoetry weatherPoetry);

    @Query("DELETE FROM weatherpoetry")
    public abstract void deleteAll();

    @Transaction
    public void updateData(List<WeatherPoetry> data){
        insertAll(data);
    }

}
