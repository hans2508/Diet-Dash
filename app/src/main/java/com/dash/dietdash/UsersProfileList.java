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

import static android.R.attr.defaultValue;

public class UsersProfileList extends Fragment {

    private ListView listView;
    private ArrayList<Makanan> listMakanan = new ArrayList<>();
    private int day;
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
        day = bundle.getInt("day", defaultValue);
        email = bundle.getString("email", "");
        getAllWidgets(rootView);
        return rootView;
    }

    private void getAllWidgets(View view) {
        listView = (ListView) view.findViewById(R.id.list);
        dbHelper = new DataBaseHelper(getActivity());
        dbHelper.openDataBase();

        listMakanan = dbHelper.getInputMakanan(email, day);
        InputMenuListAdapter listViewAdapter = new InputMenuListAdapter(UsersProfileDetail.getInstance(), listMakanan);
        ((TextView) header.findViewById(R.id.txtHeader)).setText("MENU PILIHAN USER");
        listView.addHeaderView(header);
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Makanan m = (Makanan) adapterView.getItemAtPosition(position);
                Menu menu = new Menu(0, 0, "", m.getFood(), m.getWeight(), m.getUrt(), m.getCarbs(), m.getFat(), m.getProtein(),
                        m.getCalory(), m.getChol(), m.getSodium(), m.getPotassium(), m.getCalcium());
                menu.setImage(R.drawable.food);
                NutritionAlertAdapter alert = new NutritionAlertAdapter(UsersProfileDetail.getInstance(), menu);
            }
        });
        dbHelper.close();
    }
}
