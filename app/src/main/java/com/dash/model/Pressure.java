package com.dash.model;

/**
 * Created by Hans CK on 18-Jul-16.
 */

public class Pressure {

    private String email;
    private int day;
    private int systolic;
    private int diastolic;
    private int image;
    private String info;
    private String date;

    public Pressure(String email, int day, int systolic, int diastolic, String date) {
        this.email = email;
        this.day = day;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.date = date;
    }

    public Pressure(int day, int systolic, int diastolic, int image, String info, String date) {
        this.day = day;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.image = image;
        this.info = info;
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getSystolic() {
        return systolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
