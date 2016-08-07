package com.dash.dietdash;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dash.adapter.InputMenuListAdapter;
import com.dash.adapter.NutritionAlertAdapter;
import com.dash.handler.DataBaseHelper;
import com.dash.model.Makanan;
import com.dash.model.Menu;

import java.util.ArrayList;

/**
 * Created by Hans CK on 05-Aug-16.
 */

public class UsersProfileListLunch extends Fragment{

    private ListView listView;
    private ArrayList<Makanan> listMakanan = new ArrayList<>();
    private String date;
    private String email;
    private View header;
    private View rootView;
    private DataBaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_users_profile_list, null);
        header = (View) inflater.inflate(R.layout.list_view_header, null);
        Bundle bundle = this.getArguments();
        date = bundle.getString("date", "");
        email = bundle.getString("email", "");
        getAllWidgets(rootView);
        return rootView;
    }

    private void getAllWidgets(View view) {
        listView = (ListView) view.findViewById(R.id.list);
        dbHelper = new DataBaseHelper(getActivity());
        dbHelper.openDataBase();

        listMakanan = dbHelper.getInputMakanan(email, date, "Lunch");
        InputMenuListAdapter listViewAdapter = new InputMenuListAdapter(UsersProfileListDetail.getInstance(), listMakanan);
        ((TextView) header.findViewById(R.id.txtHeader)).setText("MENU MAKAN SIANG PILIHAN USER");
        listView.addHeaderView(header);
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Makanan m = (Makanan) adapterView.getItemAtPosition(position);
                Menu menu = new Menu(0, 0, "", m.getFood(), m.getWeight(), m.getUrt(), m.getCarbs(), m.getFat(), m.getProtein(),
                        m.getCalory(), m.getChol(), m.getSodium(), m.getPotassium(), m.getCalcium());
                menu.setImage(R.drawable.food);
                NutritionAlertAdapter alert = new NutritionAlertAdapter(UsersProfileListDetail.getInstance(), menu);
            }
        });
        dbHelper.close();
    }
}

