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

import com.dash.adapter.PressureListAdapter;
import com.dash.handler.DataBaseHelper;
import com.dash.model.Pressure;

import java.util.ArrayList;

import static com.dash.dietdash.R.id.listDiary;

public class History extends AppCompatActivity {

    final Context context = this;
    private String userEmail;
    private View header;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        SharedPreferences userDetails = getSharedPreferences("dietPrefs", MODE_PRIVATE);
        userEmail = userDetails.getString("emailKey", "");

        listView = (ListView) findViewById(listDiary);
        header = (View) getLayoutInflater().inflate(R.layout.list_view_header, null);
        ((TextView) header.findViewById(R.id.txtHeader)).setText("CATATAN TEKANAN DARAH ANDA");
        listView.addHeaderView(header);

        showList();
    }

    private void showList() {
        final DataBaseHelper dbHelper = new DataBaseHelper(this);
        dbHelper.openDataBase();

        ArrayList<Pressure> listPressure = new ArrayList<>();
        listPressure = dbHelper.getPressure(userEmail);

        PressureListAdapter adapter = new PressureListAdapter(History.this, listPressure);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Pressure p = (Pressure) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(History.this, HistoryListDay.class);
                intent.putExtra("date", p.getDate());
                intent.putExtra("datePrev", p.getDatePrev());
                startActivity(intent);
            }
        });
        dbHelper.close();
    }
}
