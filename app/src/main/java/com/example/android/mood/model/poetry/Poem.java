package com.example.android.mood.model.poetry;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Joe on 4/2/18.
 */

public class Poem {
    private String title;
    private String author;
    private String[] lines;
    @SerializedName("linecount")
    private int lineCount;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String[] getLines() {
        return lines;
    }

    public int getLineCount() {
        return lineCount;
    }
}
