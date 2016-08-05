package com.dash.dietdash;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.dash.adapter.PressureListAdapter;
import com.dash.handler.DataBaseHelper;
import com.dash.model.Calory;
import com.dash.model.Pressure;

import java.util.ArrayList;

public class UsersProfilePressure extends Fragment {

    private ListView listView;
    private ArrayList<Calory> listCal;
    private String email;
    private View header;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_users_profile_pressure, null);
        header = (View) inflater.inflate(R.layout.list_view_header, null);
        Bundle bundle = this.getArguments();
        email = bundle.getString("email", "");
        getAllWidgets(rootView);
        return rootView;
    }

    private void getAllWidgets(View view) {

        listView = (ListView) view.findViewById(R.id.listDiary);

        DataBaseHelper dbHelper = new DataBaseHelper(getActivity());
        dbHelper.openDataBase();

        ArrayList<Pressure> listPressure = new ArrayList<>();
        listPressure = dbHelper.getPressure(email);

        PressureListAdapter listViewAdapter = new PressureListAdapter(UsersProfileDetail.getInstance(), listPressure);
        ((TextView) header.findViewById(R.id.txtHeader)).setText("TEKANAN DARAH HARIAN");
        listView.addHeaderView(header);
        listView.setAdapter(listViewAdapter);
        dbHelper.close();
    }
}

