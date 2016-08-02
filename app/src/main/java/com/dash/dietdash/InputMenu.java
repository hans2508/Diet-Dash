package com.dash.dietdash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.dash.adapter.GridViewAdapter;
import com.dash.model.Day;

import java.util.ArrayList;

public class InputMenu extends AppCompatActivity {

    private ArrayList<Day> dayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        dayList.add(new Day(1,R.drawable.day1));
        dayList.add(new Day(2,R.drawable.day2));
        dayList.add(new Day(3,R.drawable.day3));
        dayList.add(new Day(4,R.drawable.day4));
        dayList.add(new Day(5,R.drawable.day5));
        dayList.add(new Day(6,R.drawable.day6));
        dayList.add(new Day(7,R.drawable.day7));

        GridView gridView = (GridView) findViewById(R.id.gridView);
        GridViewAdapter gridViewAdapter = new GridViewAdapter(InputMenu.this, R.layout.grid_view_item, dayList);
        gridView.setAdapter(gridViewAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                Day d = (Day) adapterView.getItemAtPosition(position);
                int day = d.getDay();
                System.out.println("DAY ::: " + day);
                Intent intent = new Intent(InputMenu.this, InputMenuDetail.class);
                intent.putExtra("sentDay", String.valueOf(day));
                startActivity(intent);
            }
        });
    }
}

