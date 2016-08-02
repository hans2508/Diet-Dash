package com.dash.handler;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by Hans CK on 18-Jul-16.
 */

public class Calculation {

    public double calculateBMR(int gender, int age, double weight) {

        double BMR = 0;
        if (gender == 1) {
            // Calculate BMR
            if (age <= 3) {
                BMR = 60.9 * weight + 54;
            } else if (age <= 10) {
                BMR = 22.7 * weight + 495;
            } else if (age <= 18) {
                BMR = 17.5 * weight + 651;
            } else if (age <= 30) {
                BMR = 15.3 * weight + 679;
            } else if (age <= 60) {
                BMR = 11.6 * weight + 879;
            } else {
                BMR = 13.5 * weight + 487;
            }
        } else if (gender == 2) {
            // Calculate BMR
            if (age <= 3) {
                BMR = 61.0 * weight + 51;
            } else if (age <= 10) {
                BMR = 22.5 * weight + 499;
            } else if (age <= 18) {
                BMR = 12.2 * weight + 746;
            } else if (age <= 30) {
                BMR = 14.7 * weight + 496;
            } else if (age <= 60) {
                BMR = 8.7 * weight + 829;
            } else {
                BMR = 10.5 * weight + 596;
            }
        }
        return BMR;
    }


    public String calculateIMT(int gender, double weight, double height) {

        double IMT = 0;
        String kondisi_IMT = "";
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        if (gender == 1) {

            IMT = Math.round(weight / ((height / 100) * (height / 100)));
            if (IMT <= 25) {
                kondisi_IMT = "Normal (" + df.format(IMT) + ")";
            } else if (IMT <= 27) {
                kondisi_IMT = "Overweight (" + df.format(IMT) + ")";
            } else if (IMT > 27) {
                kondisi_IMT = "Obesity (" + df.format(IMT) + ")";
            }

        } else if (gender == 2) {

            IMT = weight / ((height / 100) * (height / 100));
            if (IMT <= 23) {
                kondisi_IMT = "Normal (" + df.format(IMT) + ")";
            } else if (IMT <= 27) {
                kondisi_IMT = "Overweight (" + df.format(IMT) + ")";
            } else if (IMT > 27) {
                kondisi_IMT = "Obesity (" + df.format(IMT) + ")";
            }
        }
        return kondisi_IMT;
    }

    public double calculateAKG(int gender, double BMR, String activity) {

        double AKG = 0;
        if (gender == 1) {

            switch (activity) {
                case "Sedentary":
                    AKG = 1.56 * BMR;
                    break;
                case "Moderately Active":
                    AKG = 1.76 * BMR;
                    break;
                case "Active":
                    AKG = 2.1 * BMR;
                    break;
            }
        } else if (gender == 2) {

            switch (activity) {
                case "Sedentary":
                    AKG = 1.55 * BMR;
                    break;
                case "Moderately Active":
                    AKG = 1.7 * BMR;
                    break;
                case "Active":
                    AKG = 2.0 * BMR;
                    break;
            }
        }
        return AKG;
    }

    public String determineBloodPressure(int pressure, int hg) {

        String condition = "";
        if (pressure <= 120 && hg <= 80) {
            condition = "Normal";
        } else if (pressure < 140 && hg < 90) {
            condition = "High Normal";
        } else if (pressure < 160 && hg < 100) {
            condition = "Hypertension Stage 1";
        } else {
            condition = "Hypertension Stage 2";
        }
        return condition;
    }
}
