package com.dash.dietdash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dash.adapter.DayListAdapter;
import com.dash.handler.DataBaseHelper;

import java.util.ArrayList;

public class UsersProfileDay extends Fragment {

    private ListView listView;
    private ArrayList<String> listDay = new ArrayList<>();
    private ArrayList<String> listAlternatif = new ArrayList<>();
    private String email;
    private View header;
    private View rootView;
    private DataBaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_new_makanan, null);
        header = (View) inflater.inflate(R.layout.list_view_header, null);
        Bundle bundle = this.getArguments();
        email = bundle.getString("email", "");
        getAllWidgets(rootView);
        return rootView;
    }

    private void getAllWidgets(View view) {
        listView = (ListView) view.findViewById(R.id.listNewMakanan);
        dbHelper = new DataBaseHelper(getActivity());
        dbHelper.openDataBase();

        listDay = dbHelper.getInputMakananDay(email);
        listAlternatif = dbHelper.getAlternatifDate(email);

        ArrayList<String> listTemp = new ArrayList<>();
        for (int i=0; i<listDay.size(); i++){
            listTemp.add(listDay.get(i));
        }
        for (int i=0; i<listAlternatif.size(); i++){
            listTemp.add(listAlternatif.get(i));
        }
        DayListAdapter listViewAdapter = new DayListAdapter(UsersProfileDetail.getInstance(), listTemp);
        ((TextView) header.findViewById(R.id.txtHeader)).setText("TANGGAL TERISI");
        listView.addHeaderView(header);
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String date = (String)adapterView.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), UsersProfileListDetail.class);
                intent.putExtra("email", email);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
        dbHelper.close();
    }
}
