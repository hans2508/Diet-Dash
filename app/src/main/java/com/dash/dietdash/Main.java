package com.dash.dietdash;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dash.handler.DataBaseHelper;
import com.dash.model.Pressure;
import com.dash.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Main extends AppCompatActivity {

    private User user;
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences userDetails = getSharedPreferences("dietPrefs", MODE_PRIVATE);
        String userEmail = userDetails.getString("emailKey", "");

        dbHelper = new DataBaseHelper(this);
        dbHelper.openDataBase();

        user = dbHelper.getOneUser(userEmail);

        ArrayList<Pressure> listPressure = new ArrayList<>();
        listPressure = dbHelper.getPressure(userEmail);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String date = df.format(c.getTime());
        boolean status = true;
        System.out.println("DATE : " + date);

        for (int i = 0; i < listPressure.size(); i++) {
            if(listPressure.get(i).getDate() != null) {
                if (listPressure.get(i).getDate().equals(date)) {
                    status = false;
                }
            }
        }
        if(status == true){
            Notification.Builder builder = new Notification.Builder(Main.this);
            Intent notificationIntent = new Intent(this,Main.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0);
            builder.setSmallIcon(R.drawable.day1)
                    .setContentTitle("Diet Hipertensi")
                    .setContentText("Anda belum mencatat tekanan darah harian anda!")
                    .setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Notification notification = builder.getNotification();
            notificationManager.notify(R.drawable.day1, notification);
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

    public void menu_input_pressure(View view) {

        final TextView txtPressure = (TextView) findViewById(R.id.txtPressure);
        final TextView txtInfo = (TextView) findViewById(R.id.txtInfo);

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(Main.this);
        final View promptsView = li.inflate(R.layout.alert_diary, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                String date = df.format(c.getTime());
                                if (!((EditText) promptsView.findViewById(R.id.editSystolic)).getText().toString().equals("") &&
                                        !((EditText) promptsView.findViewById(R.id.editDiastolic)).getText().toString().equals("")) {
                                    Pressure temp = new Pressure(user.getEmail(),
                                            Integer.parseInt(((EditText) promptsView.findViewById(R.id.editSystolic)).getText().toString()),
                                            Integer.parseInt(((EditText) promptsView.findViewById(R.id.editDiastolic)).getText().toString()),
                                            date, user.getPressure(), user.getHg(), user.getDate()
                                            );
                                    dbHelper.addPressure(temp);
                                } else {
                                    Toast.makeText(getBaseContext(), "Please fill in your pressure!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
