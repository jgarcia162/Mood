package com.example.android.mood;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.mood.room.MoodDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.

        assertEquals("com.example.android.mood", appContext.getPackageName());
    }

    @Test
    public void checkRoomSize() {
        MoodDatabase database = Room.databaseBuilder(appContext,MoodDatabase.class,appContext.getString(R.string.database_name)).build();
        assertNotEquals(0,database.weatherPoetryDao().getAll().size());
    }
}
