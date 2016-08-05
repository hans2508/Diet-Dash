package com.dash.model;

/**
 * Created by Hans CK on 18-Jul-16.
 */

public class Pressure {

    private String email;
    private int systolic;
    private int diastolic;
    private int image;
    private String info;
    private String date;
    private int systolicPrev;
    private int diastolicPrev;
    private String datePrev;

    public Pressure(String email, int systolic, int diastolic, String date, int systolicPrev, int diastolicPrev, String datePrev) {
        this.email = email;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.date = date;
        this.systolicPrev = systolicPrev;
        this.diastolicPrev = diastolicPrev;
        this.datePrev = datePrev;
    }

    public Pressure(String email, int systolic, int diastolic, int image, String info, String date, int systolicPrev, int diastolicPrev, String datePrev) {
        this.email = email;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.image = image;
        this.info = info;
        this.date = date;
        this.systolicPrev = systolicPrev;
        this.diastolicPrev = diastolicPrev;
        this.datePrev = datePrev;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getSystolicPrev() {
        return systolicPrev;
    }

    public void setSystolicPrev(int systolicPrev) {
        this.systolicPrev = systolicPrev;
    }

    public int getDiastolicPrev() {
        return diastolicPrev;
    }

    public void setDiastolicPrev(int diastolicPrev) {
        this.diastolicPrev = diastolicPrev;
    }

    public String getDatePrev() {
        return datePrev;
    }

    public void setDatePrev(String datePrev) {
        this.datePrev = datePrev;
    }
}
