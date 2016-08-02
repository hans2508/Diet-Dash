package com.dash.model;

/**
 * Created by Hans CK on 17-Jul-16.
 */

public class Calory {

    private int category;
    private int day;
    private String type;
    private double calories;

    public Calory(int category, int day, String type, double calories) {
        this.category = category;
        this.day = day;
        this.type = type;
        this.calories = calories;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalory(double calories) {
        this.calories = calories;
    }
}
