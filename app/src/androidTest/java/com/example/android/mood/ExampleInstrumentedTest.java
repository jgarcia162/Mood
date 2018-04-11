package com.example.android.mood;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.mood.room.MoodDatabase;
import com.example.android.mood.watson.WatsonHelper;
import com.example.android.mood.watson.WatsonListener;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.Tone;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest implements WatsonListener {
    private Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.

        assertEquals("com.example.android.mood", appContext.getPackageName());
    }

    @Test
    public void checkRoomSize() {
        MoodDatabase database = Room.databaseBuilder(appContext, MoodDatabase.class, appContext.getString(R.string.database_name)).build();
        assertNotEquals(0, database.weatherPoetryDao().getAll().size());
    }

    @Test
    public void testWatson() {
        WatsonHelper helper = WatsonHelper.getInstance();

        helper.configureToneAnalyzer(appContext);

        ToneOptions options = new ToneOptions.Builder().addTone(Tone.EMOTION).html(false).build();

        helper.getTones(getTestPoem(), options, this);
    }

    public String getTestPoem() {
        return MoodDatabase.getInstance(appContext).weatherPoetryDao().getAll().get(0).getPoem();
    }

    @Override
    public void onTonesFetched(List<ToneScore> tonesScoreList) {
        assertNotEquals(0, tonesScoreList.size());
    }

    @Test
    public void checkCredentials() {
        try {

            JSONObject credentialsJSON = new JSONObject(IOUtils.toString(appContext.getResources().openRawResource(R.raw.credentials), "UTF-8"));
            assertEquals("OtPtrc0Pdpl2", credentialsJSON.getString("password"));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
