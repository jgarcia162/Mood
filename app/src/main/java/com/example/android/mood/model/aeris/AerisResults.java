package com.example.android.mood.model.aeris;

import java.util.List;

/**
 * Created by Joe on 4/2/18.
 */

public class AerisResults {
    private AerisLocation loc;
    private List<AerisPeriod> periods;

    public AerisLocation getLoc() {
        return loc;
    }

    public List<AerisPeriod> getPeriods() {
        return periods;
    }
}
