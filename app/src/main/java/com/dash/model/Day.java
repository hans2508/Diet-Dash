package com.dash.model;

/**
 * Created by Hans CK on 16-Jul-16.
 */

public class Day {
    private int day;
    private int image;

    public Day(int day, int image) {
        this.day = day;
        this.image = image;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
