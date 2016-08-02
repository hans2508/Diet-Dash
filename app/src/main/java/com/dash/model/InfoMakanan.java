package com.dash.model;

/**
 * Created by Hans CK on 28-Jul-16.
 */

public class InfoMakanan {

    private String indikator;
    private String info;
    private int status;

    public InfoMakanan(String indikator, String info, int status) {
        this.indikator = indikator;
        this.info = info;
        this.status = status;
    }

    public String getIndikator() {
        return indikator;
    }

    public void setIndikator(String indikator) {
        this.indikator = indikator;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
