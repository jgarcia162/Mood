package com.example.android.mood.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.example.android.mood.model.WeatherPoem;

import java.util.List;

/**
 * Created by Joe on 4/7/18.
 */

@Dao
public abstract class WeatherPoetryDao {
    @Query("SELECT * FROM weatherpoetry ")
    public abstract List<WeatherPoem> getAll();

    @Insert
    public abstract void insert(WeatherPoem... weatherPoem);

    @Insert
    public abstract void insertAll(List<WeatherPoem> weatherPoemList);

    @Delete
    public abstract void delete(WeatherPoem weatherPoem);

    @Query("DELETE FROM weatherpoetry ")
    public abstract void deleteAll();

    @Query("SELECT * FROM weatherpoetry WHERE poem_title LIKE :poemTitle AND author LIKE :author LIMIT 1")
    public abstract WeatherPoem findByTitleAndAuthor(String poemTitle, String author);

    @Query("SELECT * FROM weatherpoetry WHERE author LIKE :author")
    public abstract List<WeatherPoem> findByAuthor(String author);

    @Query("SELECT * FROM weatherpoetry WHERE poem_title LIKE :title")
    public abstract List<WeatherPoem> findByTitle(String title);

    @Transaction
    public void updateData(List<WeatherPoem> data){
        insertAll(data);
    }

}
