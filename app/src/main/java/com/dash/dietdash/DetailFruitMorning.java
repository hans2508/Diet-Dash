package com.dash.dietdash;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dash.adapter.MenuListAdapter;
import com.dash.adapter.NutritionAlertAdapter;
import com.dash.handler.Calculation;
import com.dash.handler.DataBaseHelper;
import com.dash.model.Menu;
import com.dash.model.User;

import java.util.ArrayList;

import static android.R.attr.defaultValue;
import static android.content.Context.MODE_PRIVATE;

public class DetailFruitMorning extends Fragment {

    private ListView listView;
    private ArrayList<Menu> listMenu;
    private int day;
    private View header;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_food, null);
        header = (View) inflater.inflate(R.layout.list_view_header, null);
        Bundle bundle = this.getArguments();
        day = bundle.getInt("day", defaultValue);
        getAllWidgets(rootView);
        return rootView;
    }

    private void getAllWidgets(View view) {
        listView = (ListView) view.findViewById(R.id.list);
        SharedPreferences userDetails = getActivity().getSharedPreferences("dietPrefs", MODE_PRIVATE);
        String userEmail = userDetails.getString("emailKey", "");

        DataBaseHelper dbHelper = new DataBaseHelper(getActivity());
        dbHelper.openDataBase();

        // Get User Data
        User user = dbHelper.getOneUser(userEmail);

        // Calculate AKG
        Calculation c = new Calculation();
        double BMR = c.calculateBMR(user.getGender(), user.getAge(), user.getWeight());
        double AKG = c.calculateAKG(user.getGender(), BMR, user.getActivity());

        int category;
        if (AKG <= 2000) {
            category = 1800;
        } else if (AKG <= 2200) {
            category = 2200;
        } else {
            category = 2500;
        }
        listMenu = dbHelper.getMenu(category, day, "Fruit Morning");
        MenuListAdapter listViewAdapter = new MenuListAdapter(DetailSchedule.getInstance(), listMenu);
        ((TextView) header.findViewById(R.id.txtHeader)).setText("BUAH PAGI (10:00)");
        listView.addHeaderView(header);
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Menu menu = (Menu) adapterView.getItemAtPosition(position);
                NutritionAlertAdapter alert = new NutritionAlertAdapter(DetailSchedule.getInstance(), menu);

            }
        });
        dbHelper.close();
    }
}
