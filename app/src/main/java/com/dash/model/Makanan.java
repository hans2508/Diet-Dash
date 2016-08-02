package com.dash.model;

/**
 * Created by Hans CK on 23-Jul-16.
 */

public class Makanan {

    private String food;
    private int weight;
    private String urt;
    private double carbs;
    private double fat;
    private double protein;
    private double calory;
    private double chol;
    private double sodium;
    private double potassium;
    private double calcium;
    private int rowId;

    public Makanan(String food, int weight, String urt, double carbs, double fat, double protein, double calory, double chol,
                   double sodium, double potassium, double calcium) {
        this.food = food;
        this.weight = weight;
        this.urt = urt;
        this.carbs = carbs;
        this.fat = fat;
        this.protein = protein;
        this.calory = calory;
        this.chol = chol;
        this.sodium = sodium;
        this.potassium = potassium;
        this.calcium = calcium;
    }

    public String getFood() {
        return food;
    }

    public void setFood (String food) {
        this.food = food;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getUrt() {
        return urt;
    }

    public void setUrt(String urt) {
        this.urt = urt;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCalory() {
        return calory;
    }

    public void setCalory(double calory) {
        this.calory = calory;
    }

    public double getChol() {
        return chol;
    }

    public void setChol(double chol) {
        this.chol = chol;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    public double getPotassium() {
        return potassium;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }

    public double getCalcium() {
        return calcium;
    }

    public void setCalcium(double calcium) {
        this.calcium = calcium;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }
}
