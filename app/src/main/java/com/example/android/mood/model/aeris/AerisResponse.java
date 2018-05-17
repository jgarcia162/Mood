package com.example.android.mood.model.aeris;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Joe on 4/2/18.
 */

public class AerisResponse {
    private boolean success;
    @SerializedName("response")
    private List<AerisResults> results;

    public boolean isSuccess() {
        return success;
    }

    public List<AerisResults> getResults() {
        return results;
    }

    public List<AerisPeriod> getPeriods(){
        return results.get(0).getPeriods();
    }
}
