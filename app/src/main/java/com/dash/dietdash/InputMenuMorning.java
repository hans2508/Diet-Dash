package com.dash.dietdash;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dash.adapter.InputMenuListAdapter;
import com.dash.adapter.NutritionAlertAdapter;
import com.dash.handler.DataBaseHelper;
import com.dash.model.Makanan;
import com.dash.model.Menu;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class InputMenuMorning extends Fragment {

    private ListView listView;
    private ArrayList<Makanan> listMakanan = new ArrayList<>();
    ArrayList<Integer> listRow = new ArrayList<>();
    private String date;
    private View header;
    private View rootView;
    private DataBaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_input_menu_list, null);
        listView = (ListView) rootView.findViewById(R.id.list);
        header = (View) inflater.inflate(R.layout.list_view_header, null);
        ((TextView) header.findViewById(R.id.txtHeader)).setText("MENU SARAPAN PILIHANMU");
        listView.addHeaderView(header);

        Bundle bundle = this.getArguments();
        date = bundle.getString("date", "");
        getAllWidgets(rootView);
        return rootView;
    }

    private void getAllWidgets(View view) {

        SharedPreferences userDetails = getActivity().getSharedPreferences("dietPrefs", MODE_PRIVATE);
        final String userEmail = userDetails.getString("emailKey", "");

        dbHelper = new DataBaseHelper(getActivity());
        dbHelper.openDataBase();

        final EditText editSearch = (EditText) view.findViewById(R.id.editSearch);
        editSearch.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    listMakanan = dbHelper.getMakanan(editSearch.getText().toString());
                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
                    builderSingle.setTitle("Pilih Menu Makanan :");

                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            getActivity(), android.R.layout.simple_list_item_1);
                    for (int i = 0; i < listMakanan.size(); i++) {
                        arrayAdapter.add(listMakanan.get(i).getFood());
                    }

                    builderSingle.setNegativeButton(
                            "Done",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    builderSingle.setAdapter(
                            arrayAdapter,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String food = arrayAdapter.getItem(which);
                                    System.out.println("MAKANAN: " + food);
                                    dbHelper.addInputMakanan(userEmail, date, food, "Morning");
                                    getAllWidgets(rootView);
                                }
                            });
                    if (listMakanan.size() == 0) {
                        dbHelper.addSearchFood(editSearch.getText().toString());
                        Toast.makeText(getActivity(), "No Food with '" + editSearch.getText().toString() + "' found!", Toast.LENGTH_SHORT).show();
                    } else {
                        builderSingle.show();
                    }
                }
            }
        });

        listMakanan = dbHelper.getInputMakanan(userEmail, date, "Morning");
        listRow = dbHelper.getRowID(userEmail, date, "Morning");
        InputMenuListAdapter listViewAdapter = new InputMenuListAdapter(InputMenuDetail.getInstance(), listMakanan);
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Makanan m = (Makanan) adapterView.getItemAtPosition(position);
                Menu menu = new Menu(0, 0, "", m.getFood(), m.getWeight(), m.getUrt(), m.getCarbs(), m.getFat(), m.getProtein(),
                        m.getCalory(), m.getChol(), m.getSodium(), m.getPotassium(), m.getCalcium());
                menu.setImage(R.drawable.food);
                NutritionAlertAdapter alert = new NutritionAlertAdapter(InputMenuDetail.getInstance(), menu);
            }
        });

        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                removeItemFromList(position);

                return true;
            }
        });
        dbHelper.close();
    }

    // method to remove list item
    protected void removeItemFromList(final int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub

                // main code on after clicking yes
                dbHelper.deleteInputMakanan(listRow.get(position - 1));
                getAllWidgets(rootView);
            }
        });
        alert.setNegativeButton("CANCEL", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();

    }
}
