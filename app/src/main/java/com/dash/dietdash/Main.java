package com.dash.dietdash;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dash.handler.DataBaseHelper;
import com.dash.model.Pressure;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences userDetails = getSharedPreferences("dietPrefs", MODE_PRIVATE);
        String userEmail = userDetails.getString("emailKey", "");
        final DataBaseHelper dbHelper = new DataBaseHelper(this);
        dbHelper.openDataBase();
        ArrayList<Pressure> listPressure = new ArrayList<>();
        listPressure = dbHelper.getPressure(userEmail);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String date = df.format(c.getTime());
        boolean status = true;

        for (int i = 0; i < listPressure.size(); i++) {
            if(listPressure.get(i).getDate() != null) {
                if (listPressure.get(i).getDate().equals(date)) {
                    System.out.println(listPressure.get(i).getDate());
                    status = false;
                }
            }
        }
        if(status == true){
            AlertDialog alertDialog = new AlertDialog.Builder(Main.this).create();
            alertDialog.setTitle("Reminder");
            alertDialog.setMessage("You have not input your blood pressure today!\nPlease go to your Diary to monitor your condition");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        dbHelper.close();
    }

    public void menu_profile(View view) {

        Intent intent = new Intent(Main.this, Profile.class);
        startActivity(intent);
    }

    public void menu_schedule(View view) {

        Intent intent = new Intent(Main.this, Schedule.class);
        startActivity(intent);
    }

    public void menu_diary(View view) {

        Intent intent = new Intent(Main.this, Diary.class);
        startActivity(intent);
    }

    public void menu_about(View view) {

        Intent intent = new Intent(Main.this, About.class);
        startActivity(intent);
    }

    public void menu_input_menu(View view) {

        Intent intent = new Intent(Main.this, InputMenu.class);
        startActivity(intent);
    }

}
