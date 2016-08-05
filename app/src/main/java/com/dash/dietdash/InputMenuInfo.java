package com.dash.dietdash;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.dash.adapter.InfoMakananListAdapter;
import com.dash.handler.Calculation;
import com.dash.handler.DataBaseHelper;
import com.dash.model.Calory;
import com.dash.model.InfoMakanan;
import com.dash.model.Makanan;
import com.dash.model.User;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class InputMenuInfo extends Fragment {

    private ListView listView;
    private ArrayList<Calory> listCal;
    private String date;
    private View header;
    protected final double CONST_SODIUM = 2300;
    protected final double CONST_CHOL = 300;
    protected final double CONST_CALCIUM = 808;
    protected final double CONST_POTASSIUM = 4000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_input_menu_info, null);
        header = (View) inflater.inflate(R.layout.list_view_header, null);
        Bundle bundle = this.getArguments();
        date = bundle.getString("date", "");
        getAllWidgets(rootView);
        return rootView;
    }

    private void getAllWidgets(View view) {

        listView = (ListView) view.findViewById(R.id.listInputMenuInfo);
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

        // Get Input Makanan
        ArrayList<Makanan> listMakanan = new ArrayList<>();
        listMakanan = dbHelper.getAllInputMakanan(userEmail, date);

        double totSodium = 0, totChol = 0, totCalcium = 0, totCarbs = 0, totPotassium = 0, totFat = 0, totProtein = 0, totCallory = 0;
        for (int i = 0; i < listMakanan.size(); i++) {
            totCallory += listMakanan.get(i).getCalory();
            totSodium += listMakanan.get(i).getSodium();
            totPotassium += listMakanan.get(i).getPotassium();
            totChol += listMakanan.get(i).getChol();
            totCalcium += listMakanan.get(i).getCalcium();
            totCarbs += listMakanan.get(i).getCarbs();
            totProtein += listMakanan.get(i).getProtein();
            totFat += listMakanan.get(i).getFat();
        }

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        double sisa = AKG - totCallory;
        String kal = df.format(AKG) + " - " + totCallory + " = " + df.format(sisa);

        // Give status if = 0 then OK, else then Nutrition Over!
        ArrayList<InfoMakanan> listInfo = new ArrayList<>();
        if (sisa >= 0) {
            listInfo.add(new InfoMakanan("KALORI", kal + " kkal", 0));
        } else {
            listInfo.add(new InfoMakanan("KALORI", kal + " kkal", 1));
        }
        listInfo.add(new InfoMakanan("KARBOHIDRAT", df.format(totCarbs) + " gram", 0));
        listInfo.add(new InfoMakanan("LEMAK", df.format(totFat) + " gram", 0));
        listInfo.add(new InfoMakanan("PROTEIN", df.format(totProtein) + " gram", 0));
        if (totChol <= CONST_CHOL) {
            listInfo.add(new InfoMakanan("KOLESTEROL", String.valueOf(totChol) + " < " + CONST_CHOL + " mg", 0));
        } else {
            listInfo.add(new InfoMakanan("KOLESTEROL", String.valueOf(totChol) + " < " + CONST_CHOL + " mg", 1));
        }
        if (totSodium <= CONST_SODIUM) {
            listInfo.add(new InfoMakanan("SODIUM", String.valueOf(totSodium) + " < " + CONST_SODIUM + " mg", 0));
        } else {
            listInfo.add(new InfoMakanan("SODIUM", String.valueOf(totSodium) + " < " + CONST_SODIUM + " mg", 1));
        }
        System.out.println("POTASIUM " + totPotassium);
        if (totPotassium <= CONST_POTASSIUM) {
            listInfo.add(new InfoMakanan("KALIUM", String.valueOf(totPotassium) + " < " + CONST_POTASSIUM + " mg", 0));
            System.out.println("MASUK 0");
        } else {
            listInfo.add(new InfoMakanan("KALIUM", String.valueOf(totPotassium) + " < " + CONST_POTASSIUM + " mg", 1));
            System.out.println("MASUK 1");
        }
        if (totCalcium <= CONST_CALCIUM) {
            listInfo.add(new InfoMakanan("KALSIUM", String.valueOf(totCalcium) + " < " + CONST_CALCIUM + " mg", 0));
        } else {
            listInfo.add(new InfoMakanan("KALSIUM", String.valueOf(totCalcium) + " < " + CONST_CALCIUM + " mg", 1));
        }

        InfoMakananListAdapter listViewAdapter = new InfoMakananListAdapter(InputMenuDetail.getInstance(), listInfo);
        ((TextView) header.findViewById(R.id.txtHeader)).setText("PANDUAN NUTRISI HARIAN ANDA");
        listView.addHeaderView(header);
        listView.setAdapter(listViewAdapter);
    }
}
