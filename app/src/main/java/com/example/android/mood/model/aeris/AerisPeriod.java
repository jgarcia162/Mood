package com.example.android.mood.model.aeris;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Joe on 4/2/18.
 */

public class AerisPeriod {
    private long timestamp;
    private int maxTempC;
    private int maxTempF;
    private int minTempC;
    private int minTempF;
    @SerializedName("weather")
    private String weatherDescription;
    private String weatherPrimary;

    private String dateTimeISO;


    public String getDateTimeISO() {
        return dateTimeISO;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getMaxTempC() {
        return maxTempC;
    }

    public int getMaxTempF() {
        return maxTempF;
    }

    public int getMinTempC() {
        return minTempC;
    }

    public int getMinTempF() {
        return minTempF;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getWeatherPrimary() {
        return weatherPrimary;
    }

    public String getDay() {
        return new DateTime(this.timestamp).dayOfWeek().getAsText();
    }

    public String getDayOfTheWeek() {
        String dayOfTheWeek = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss-HH:mm", Locale.getDefault());
        try {
            Date date = simpleDateFormat.parse(this.dateTimeISO); //2016-12-23T07:00:00-05:00
            dayOfTheWeek = new SimpleDateFormat("E",Locale.getDefault()).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayOfTheWeek;
    }


    //TODO(Low) clean this, how can we make it more efficient?
    public String getFullDayOfTheWeekName() {
        switch (getDayOfTheWeek()) {
            case "Mon":
                return "Monday";
            case "Tue":
                return "Tuesday";
            case "Wed":
                return "Wednesday";
            case "Thu":
                return "Thursday";
            case "Fri":
                return "Friday";
            case "Sat":
                return "Saturday";
            case "Sun":
                return "Sunday";
        }
        return "Today";
    }



}
