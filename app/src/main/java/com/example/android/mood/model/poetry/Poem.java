package com.example.android.mood.model.poetry;

/**
 * Created by Joe on 4/2/18.
 */

public class Poem {
    private String title;
    private String author;
    private String[] lines;
    private String mood;

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String[] getLines() {
        return lines;
    }

    public String getFullPoem() {
        StringBuilder sb = new StringBuilder();
        for (String line : this.lines) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
}
