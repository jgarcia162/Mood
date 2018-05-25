package com.example.android.mood.model.weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Joe on 4/2/18.
 */

public class AerisLocation {
    @SerializedName("long")
    private double lon;
    private double lat;

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }
}
