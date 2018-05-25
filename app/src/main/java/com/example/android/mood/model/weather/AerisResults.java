package com.example.android.mood.model.weather;

import java.util.List;

/**
 * Created by Joe on 4/2/18.
 */

public class AerisResults {
    private AerisLocation loc;
    private List<Weather> periods;

    public AerisLocation getLoc() {
        return loc;
    }

    public List<Weather> getPeriods() {
        return periods;
    }
}
