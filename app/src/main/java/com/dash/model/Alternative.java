package com.dash.model;

/**
 * Created by Hans CK on 08-Aug-16.
 */

public class Alternative {

    private String email;
    private int category;
    private int day;
    private String date;

    public Alternative() {
    }

    public Alternative(String email, int category, int day, String date) {
        this.email = email;
        this.category = category;
        this.day = day;
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
