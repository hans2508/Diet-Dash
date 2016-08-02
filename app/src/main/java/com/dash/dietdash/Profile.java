package com.dash.dietdash;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dash.handler.Calculation;
import com.dash.handler.DataBaseHelper;
import com.dash.model.User;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Profile extends AppCompatActivity implements OnItemSelectedListener {

    private int gender;
    private Spinner spinner_activity;
    public static String temp;
    private static String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        addListenerOnSpinnerItemSelection();

        SharedPreferences userDetails = getSharedPreferences("dietPrefs", MODE_PRIVATE);
        userEmail = userDetails.getString("emailKey", "");
        setData(userEmail);
    }

    private void setData(String userEmail) {
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        dbHelper.openDataBase();

        // Get User Data
        User user = dbHelper.getOneUser(userEmail);
        if (user.getName() != null) {
            ((EditText) findViewById(R.id.editName)).setText(user.getName());
            ((EditText) findViewById(R.id.editAge)).setText(String.valueOf(user.getAge()));
            ((EditText) findViewById(R.id.editWeight)).setText(String.valueOf(user.getWeight()));
            ((EditText) findViewById(R.id.editHeight)).setText(String.valueOf(user.getHeight()));
            ((EditText) findViewById(R.id.editPressure)).setText(String.valueOf(user.getPressure()));
            ((EditText) findViewById(R.id.editHg)).setText(String.valueOf(user.getHg()));
            RadioButton rbu1 = (RadioButton) findViewById(R.id.radioMale);
            RadioButton rbu2 = (RadioButton) findViewById(R.id.radioFemale);
            if (user.getGender() == 1) {
                rbu1.setChecked(true);
            } else if (user.getGender() == 2) {
                rbu2.setChecked(true);
            }
            if (user.getActivity().equals("Sedentary")) {
                spinner_activity.setSelection(1);
            } else if (user.getActivity().equals("Moderately Active")) {
                spinner_activity.setSelection(2);
            } else if (user.getActivity().equals("Active")) {
                spinner_activity.setSelection(3);
            }

            gender = user.getGender();
            calculateData(user.getName(), user.getAge(), user.getWeight(), user.getHeight(), user.getPressure(), user.getHg(), user.getActivity());
            dbHelper.close();
        }
    }

    public void saveProfile(View view) {

        if (((EditText) findViewById(R.id.editName)).getText().toString() != "" &&
                ((EditText) findViewById(R.id.editName)).getText().toString() != "" &&
                ((EditText) findViewById(R.id.editWeight)).getText().toString() != "" &&
                ((EditText) findViewById(R.id.editHeight)).getText().toString() != "" &&
                ((EditText) findViewById(R.id.editPressure)).getText().toString() != "" &&
                ((EditText) findViewById(R.id.editHg)).getText().toString() != "" && gender != 0) {
            String name = ((EditText) findViewById(R.id.editName)).getText().toString();
            int age = Integer.parseInt(((EditText) findViewById(R.id.editAge)).getText().toString());
            double weight = Double.parseDouble(((EditText) findViewById(R.id.editWeight)).getText().toString());
            double height = Double.parseDouble(((EditText) findViewById(R.id.editHeight)).getText().toString());
            int pressure = Integer.parseInt(((EditText) findViewById(R.id.editPressure)).getText().toString());
            int hg = Integer.parseInt(((EditText) findViewById(R.id.editHg)).getText().toString());
            String activity = String.valueOf(spinner_activity.getSelectedItem());
            if (activity.equals("Choose activity")) {
                Toast.makeText(getBaseContext(), "Please complete all data first!", Toast.LENGTH_SHORT).show();
            } else {
                calculateData(name, age, weight, height, pressure, hg, activity);
            }
        } else {
            Toast.makeText(getBaseContext(), "Please complete all data first!", Toast.LENGTH_SHORT).show();
        }
    }

    private void calculateData(String name, int age, double weight, double height, int pressure, int hg, String activity) {
        double BMR = 0, AKG = 0, idealWeight = 0;
        String kondisi_IMT = "", blood = "";
        Calculation c = new Calculation();

        if (gender == 1) {

            BMR = c.calculateBMR(gender, age, weight);
            AKG = c.calculateAKG(gender, BMR, activity);
            kondisi_IMT = c.calculateIMT(gender, weight, height);

        } else if (gender == 2) {

            BMR = c.calculateBMR(gender, age, weight);
            AKG = c.calculateAKG(gender, BMR, activity);
            kondisi_IMT = c.calculateIMT(gender, weight, height);
        }
        idealWeight = (height - 100) - (0.1 * (height - 100));
        blood = c.determineBloodPressure(pressure, hg);


        // Save Profile to database
        User user = new User(userEmail, "", name, gender, age, weight, height, pressure, hg, activity);
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        dbHelper.openDataBase();
        dbHelper.addProfile(user);

        TextView textBMR = (TextView) findViewById(R.id.textBMR);
        TextView textAKG = (TextView) findViewById(R.id.textAKG);
        TextView textIMT = (TextView) findViewById(R.id.textIMT);
        TextView textBlood = (TextView) findViewById(R.id.textBlood);
        TextView textIdealWeight = (TextView) findViewById(R.id.textIdealWeight);
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        textBMR.setText(String.valueOf(df.format(BMR)) + " kkal");
        textAKG.setText(String.valueOf(df.format(AKG)) + " kkal");
        textIMT.setText(kondisi_IMT);
        textIdealWeight.setText(String.valueOf(idealWeight) + " kg");
        textBlood.setText(blood);
        dbHelper.close();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioMale:
                if (checked)
                    gender = 1;
                break;
            case R.id.radioFemale:
                if (checked)
                    gender = 2;
                break;
        }
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner_activity = (Spinner) findViewById(R.id.spinner_activity);
        spinner_activity.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        String activity = String.valueOf(spinner_activity.getSelectedItem());
        if (activity.equals("Sedentary")) {
            alertDialog.setTitle("Sedentary");
            alertDialog.setMessage("Pada level ini pengidap hipertensi melakukan aktivitas rutin yang dilakukan sehari-hari");
            alertDialog.setPositiveButton("OK", null);
            alertDialog.show();
        } else if (activity.equals("Moderately Active")) {
            alertDialog.setTitle("Moderately Active");
            alertDialog.setMessage("Pada level ini pengidap melakukan aktivitas fisik dengan berjalan sekitar 2-5 km setiap harinya");
            alertDialog.setPositiveButton("OK", null);
            alertDialog.show();
        } else if (activity.equals("Active")) {
            alertDialog.setTitle("Active");
            alertDialog.setMessage("Pada level ini pengidap melakukan aktivitas fisik seperti berjalan 5-6 km per jam");
            alertDialog.setPositiveButton("OK", null);
            alertDialog.show();
        }

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void nextToHome(View view) {

        Intent intent = new Intent(Profile.this, Main.class);
        startActivity(intent);
    }
}
