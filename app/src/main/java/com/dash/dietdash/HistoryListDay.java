package com.dash.dietdash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dash.adapter.DayListAdapter;
import com.dash.handler.DataBaseHelper;
import com.dash.model.Alternative;

import java.util.ArrayList;

/**
 * Created by Hans CK on 07-Aug-16.
 */

public class HistoryListDay extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> listDay;
    private ArrayList<String> listAlternatif;
    private String date, datePrev;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_makanan);
        context = this;

        SharedPreferences userDetails = getSharedPreferences("dietPrefs", MODE_PRIVATE);
        final String userEmail = userDetails.getString("emailKey", "");
        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        datePrev = intent.getStringExtra("datePrev");

        listView = (ListView) findViewById(R.id.listNewMakanan);
        final DataBaseHelper dbHelper = new DataBaseHelper(HistoryListDay.this);
        dbHelper.openDataBase();

        listDay = dbHelper.getInputMakananDay(userEmail);
        listAlternatif = dbHelper.getAlternatifDate(userEmail);
        final ArrayList<String> listTemp = new ArrayList<>();

        String[] date1 = datePrev.split("-");
        String[] date2 = date.split("-");
        for (int i = 0; i < listDay.size(); i++) {
            String[] temp = listDay.get(i).split("-");
            if (Integer.parseInt(temp[0]) >= Integer.parseInt(date1[0]) &&
                    Integer.parseInt(temp[0]) <= Integer.parseInt(date2[0]) &&
                    convert(temp[1]) >= convert(date1[1]) &&
                    convert(temp[1]) <= convert(date2[1])) {
                listTemp.add(listDay.get(i));
            }
        }

        for (int i = 0; i < listAlternatif.size(); i++) {
            String[] temp = listAlternatif.get(i).split("-");
            if (Integer.parseInt(temp[0]) >= Integer.parseInt(date1[0]) &&
                    Integer.parseInt(temp[0]) <= Integer.parseInt(date2[0]) &&
                    convert(temp[1]) >= convert(date1[1]) &&
                    convert(temp[1]) <= convert(date2[1])) {
                listTemp.add(listAlternatif.get(i));
            }
        }

        DayListAdapter listViewAdapter = new DayListAdapter(HistoryListDay.this, listTemp);
        View header = (View) getLayoutInflater().inflate(R.layout.list_view_header, null);
        ((TextView) header.findViewById(R.id.txtHeader)).setText("TANGGAL TERISI");
        listView.addHeaderView(header);
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                String date = (String) adapterView.getItemAtPosition(position);
                ArrayList<Alternative> list = new ArrayList<>();
                list = dbHelper.getAlternatif(userEmail);
                boolean status = true;
                Alternative a = null;
                for (int j = 0; j < list.size(); j++) {
                    if (date.equals(list.get(j).getDate())) {
                        a = new Alternative(list.get(j).getEmail(), list.get(j).getCategory(), list.get(j).getDay(), list.get(j).getDate());
                        status = false;
                        break;
                    }
                }
                Intent intent;
                if(status == true) {
                    intent = new Intent(HistoryListDay.this, UsersProfileListDetail.class);
                    intent.putExtra("date", date);
                    intent.putExtra("email", userEmail);
                } else{
                    intent = new Intent(HistoryListDay.this, DetailSchedule.class);
                    intent.putExtra("sentDay", String.valueOf(a.getDay()));
                    intent.putExtra("status", String.valueOf(1));
                }
                startActivity(intent);
            }
        });
        dbHelper.close();
    }

    private int convert(String month){
        int m = 0;
        switch(month){
            case "Jan" : m = 1;
                break;
            case "Feb" : m = 2;
                break;
            case "Mar" : m = 3;
                break;
            case "Apr" : m = 4;
                break;
            case "May" : m = 5;
                break;
            case "Jun" : m = 6;
                break;
            case "Jul" : m = 7;
                break;
            case "Aug" : m = 8;
                break;
            case "Sep" : m = 9;
                break;
            case "Oct" : m = 10;
                break;
            case "Nov" : m = 11;
                break;
            case "Des" : m = 12;
                break;
        }
        return m;
    }
}
