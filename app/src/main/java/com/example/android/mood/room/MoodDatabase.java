package com.example.android.mood.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.mood.R;
import com.example.android.mood.model.WeatherPoetry;

/**
 * Created by Joe on 4/7/18.
 */

@Database(entities = {WeatherPoetry.class}, version = 1)
public abstract class MoodDatabase extends RoomDatabase {
    private static MoodDatabase database;

    public abstract WeatherPoetryDao weatherPoetryDao();

    public static MoodDatabase getInstance(Context context) {
        if (database == null) {
            database = Room
                    .databaseBuilder(context.getApplicationContext(), MoodDatabase.class, context.getString(R.string.database_name))
                    .build();
        }
        return database;
    }

    public void destroyInstance(){
        database= null;
    }
}
