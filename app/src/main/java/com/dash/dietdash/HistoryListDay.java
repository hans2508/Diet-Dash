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

import java.util.ArrayList;

/**
 * Created by Hans CK on 07-Aug-16.
 */

public class HistoryListDay extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> listDay = new ArrayList<>();
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
        DataBaseHelper dbHelper = new DataBaseHelper(HistoryListDay.this);
        dbHelper.openDataBase();

        listDay = dbHelper.getInputMakananDay(userEmail);
        ArrayList<String> listTemp = new ArrayList<>();

        String[] date1 = datePrev.split("-");
        String[] date2 = date.split("-");
        for (int i = 0; i < listDay.size(); i++) {
            String[] temp = listDay.get(i).split("-");
            if (Integer.parseInt(temp[0]) >= Integer.parseInt(date1[0]) &&
                    Integer.parseInt(temp[0]) <= Integer.parseInt(date2[0])) {
                listTemp.add(listDay.get(i));
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
                Intent intent = new Intent(HistoryListDay.this, UsersProfileListDetail.class);
                intent.putExtra("date", date);
                intent.putExtra("email", userEmail);
                startActivity(intent);
            }
        });
        dbHelper.close();
    }
}
