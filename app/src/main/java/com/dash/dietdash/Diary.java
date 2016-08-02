package com.dash.dietdash;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dash.adapter.DiaryListAdapter;
import com.dash.handler.DataBaseHelper;
import com.dash.model.Pressure;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.dash.dietdash.R.id.listDiary;

public class Diary extends AppCompatActivity {

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
        Pressure p[] = new Pressure[]{
                new Pressure(1, 0, 0, R.drawable.day1, "", ""),
                new Pressure(2, 0, 0, R.drawable.day2, "", ""),
                new Pressure(3, 0, 0, R.drawable.day3, "", ""),
                new Pressure(4, 0, 0, R.drawable.day4, "", ""),
                new Pressure(5, 0, 0, R.drawable.day5, "", ""),
                new Pressure(6, 0, 0, R.drawable.day6, "", ""),
                new Pressure(7, 0, 0, R.drawable.day7, "", "")
        };
        for (int i = 0; i < listPressure.size(); i++) {
            System.out.println("LIST DIARY : " + i + " " + listPressure.get(i).getSystolic() + "/" +
                    listPressure.get(i).getDiastolic());
            p[i].setSystolic(listPressure.get(i).getSystolic());
            p[i].setDiastolic(listPressure.get(i).getDiastolic());
            if (i > 0) {
                if ((listPressure.get(i).getSystolic() != 0 && listPressure.get(i).getDiastolic() != 0) &&
                        (listPressure.get(i).getSystolic() < listPressure.get(i - 1).getSystolic() ||
                                listPressure.get(i).getDiastolic() < listPressure.get(i - 1).getDiastolic())) {
                    p[i].setInfo("TURUN");
                } else {
                    p[i].setInfo("NAIK");
                }
            } else {
                p[i].setInfo("BELUM ADA");
            }
        }

        DiaryListAdapter adapter = new DiaryListAdapter(this, R.layout.diary_list_item, p);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                final Pressure pres = (Pressure) adapterView.getItemAtPosition(position);
                final TextView txtPressure = (TextView) findViewById(R.id.txtPressure);
                final TextView txtInfo = (TextView) findViewById(R.id.txtInfo);

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                final View promptsView = li.inflate(R.layout.alert_diary, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

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
                                            Pressure temp = new Pressure(userEmail, pres.getDay(),
                                                    Integer.parseInt(((EditText) promptsView.findViewById(R.id.editSystolic)).getText().toString()),
                                                    Integer.parseInt(((EditText) promptsView.findViewById(R.id.editDiastolic)).getText().toString()),
                                                    date);
                                            dbHelper.addPressure(temp);
                                            showList();
                                        } else{
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
        });
        dbHelper.close();
    }
}
