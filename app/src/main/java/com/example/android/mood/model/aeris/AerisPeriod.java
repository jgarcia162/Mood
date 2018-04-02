package com.example.android.mood.model.aeris;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Joe on 4/2/18.
 */

public class AerisPeriod {
    private long timestamp;
    private int maxTempC;
    private int maxTempF;
    private int minTempC;
    private int minTempF;
    private int uvi;
    private int feelsLikeC;
    private int feelsLikeF;
    @SerializedName("weather")
    private String weatherDescription;
    private String weatherPrimary;
    private long sunrise;
    private long sunset;

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

    public int getUvi() {
        return uvi;
    }

    public int getFeelsLikeC() {
        return feelsLikeC;
    }

    public int getFeelsLikeF() {
        return feelsLikeF;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getWeatherPrimary() {
        return weatherPrimary;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }
}
