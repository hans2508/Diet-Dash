package com.dash.dietdash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

import com.dash.handler.DataBaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class InputMenu extends AppCompatActivity {

    private CalendarView calendar;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences userDetails = getSharedPreferences("dietPrefs", MODE_PRIVATE);
        userEmail = userDetails.getString("emailKey", "");

        //sets the main layout of the activity
        setContentView(R.layout.activity_input_menu);

        //initializes the calendarview
        initializeCalendar();
    }

    public void initializeCalendar() {
        calendar = (CalendarView) findViewById(R.id.calendar);

        // sets whether to show the week number.
        calendar.setShowWeekNumber(false);

        // sets the first day of week according to Calendar.
        // here we set Monday as the first day of the Calendar
        calendar.setFirstDayOfWeek(2);

        //The background color for the selected week.
        calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.lightblue));

        //sets the color for the dates of an unfocused month.
        calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.transparent));

        //sets the color for the separator line between weeks.
        calendar.setWeekSeparatorLineColor(getResources().getColor(R.color.transparent));

        //sets the color for the vertical bar shown at the beginning and at the end of the selected date.
        calendar.setSelectedDateVerticalBar(R.color.blue);

        //sets the listener to be notified upon selected date change.
        calendar.setOnDateChangeListener(new OnDateChangeListener() {
            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String date = df.format(c.getTime());
                String[] date1 = date.split("-");
                String temp = day + "-" + date1[1] + "-" + year;

                DataBaseHelper dbHelper = new DataBaseHelper(InputMenu.this);
                ArrayList<String> list = dbHelper.getAlternatifDate(userEmail);
                boolean status = true;
                for (int i = 0; i < list.size(); i++) {

                    if (list.get(i).equals(temp)) {
                        status = false;
                    }
                }
                if (status == true) {

                    Intent intent = new Intent(InputMenu.this, InputMenuDetail.class);
                    intent.putExtra("date", temp);
                    startActivity(intent);
                } else {
                    Toast.makeText(getBaseContext(), "Anda sudah memasukkan menu rekomendasi untuk hari ini!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}