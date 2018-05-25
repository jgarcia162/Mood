package com.example.android.mood.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.android.mood.R;
import com.example.android.mood.model.WeatherPoetry;

/**
 * Created by Joe on 4/7/18.
 */

@Database(entities = {WeatherPoetry.class}, version = 3)
public abstract class MoodDatabase extends RoomDatabase {
    private static MoodDatabase database;

    public abstract WeatherPoetryDao weatherPoetryDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            database.execSQL("CREATE TABLE new_weatherpoetry (weather TEXT, poem TEXT, PRIMARY KEY(tid))");

            database.execSQL("INSERT INTO new_weatherpoetry (poem, weather) SELECT weather, poem FROM weatherpoetry");

            database.execSQL("DROP TABLE weatherpoetry");

            database.execSQL("ALTER TABLE new_weatherpoetry RENAME TO weatherpoetry");

        }
    };


    public static MoodDatabase getInstance(Context context) {
        if (database == null) {
            database = Room
                    .databaseBuilder(context.getApplicationContext(), MoodDatabase.class, context.getString(R.string.database_name))
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public void destroyInstance() {
        database = null;
    }
}
