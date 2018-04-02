package com.example.android.mood.model;

import com.example.android.mood.model.aeris.AerisPeriod;
import com.example.android.mood.model.poetry.Poem;

/**
 * Created by Joe on 4/2/18.
 */

public class AerisPoetry {
    private AerisPeriod aerisPeriod;
    private Poem poem;

    public AerisPoetry(AerisPeriod aerisPeriod, Poem poem) {
        this.aerisPeriod = aerisPeriod;
        this.poem = poem;
    }

    public AerisPeriod getAerisPeriod() {
        return aerisPeriod;
    }

    public Poem getPoem() {
        return poem;
    }
}
