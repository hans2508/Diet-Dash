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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dash.adapter.NewMakananListAdapter;
import com.dash.handler.DataBaseHelper;
import com.dash.model.Makanan;

import java.util.ArrayList;

public class NewMakanan extends AppCompatActivity {

    final Context context = this;
    String userEmail;
    private DataBaseHelper dbHelper;
    private View header;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_makanan);
        SharedPreferences userDetails = getSharedPreferences("dietPrefs", MODE_PRIVATE);
        userEmail = userDetails.getString("emailKey", "");

        listView = (ListView) findViewById(R.id.listNewMakanan);
        header = (View)getLayoutInflater().inflate(R.layout.list_view_header, null);
        ((TextView) header.findViewById(R.id.txtHeader)).setText("MAKANAN YANG DICARI USER");
        listView.addHeaderView(header);

        showList();
    }

    private void showList() {
        dbHelper = new DataBaseHelper(this);
        dbHelper.openDataBase();

        ArrayList<String> listSearch = new ArrayList<>();
        listSearch = dbHelper.getSearchFood();

        String temp[] = listSearch.toArray(new String[listSearch.size()]);
        final NewMakananListAdapter adapter = new NewMakananListAdapter(this, R.layout.new_makanan_list_item, temp);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                final String search = (String) adapterView.getItemAtPosition(position);

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                final View promptsView = li.inflate(R.layout.alert_new_makanan, null);

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
                                        Makanan makanan = new Makanan(
                                                ((EditText) promptsView.findViewById(R.id.editName)).getText().toString(),
                                                Integer.parseInt(((EditText) promptsView.findViewById(R.id.editWeight)).getText().toString()),
                                                ((EditText) promptsView.findViewById(R.id.editURT)).getText().toString(),
                                                Double.parseDouble(((EditText) promptsView.findViewById(R.id.editCarbs)).getText().toString()),
                                                Double.parseDouble(((EditText) promptsView.findViewById(R.id.editFat)).getText().toString()),
                                                Double.parseDouble(((EditText) promptsView.findViewById(R.id.editProtein)).getText().toString()),
                                                Double.parseDouble(((EditText) promptsView.findViewById(R.id.editCal)).getText().toString()),
                                                Double.parseDouble(((EditText) promptsView.findViewById(R.id.editChol)).getText().toString()),
                                                Double.parseDouble(((EditText) promptsView.findViewById(R.id.editSodium)).getText().toString()),
                                                Double.parseDouble(((EditText) promptsView.findViewById(R.id.editPotassium)).getText().toString()),
                                                Double.parseDouble(((EditText) promptsView.findViewById(R.id.editCalcium)).getText().toString())
                                        );
                                        dbHelper.addNewMakanan(makanan);
                                        dbHelper.deleteSearchFood(search);
                                        Toast.makeText(NewMakanan.this, "New makanan has been inserted!", Toast.LENGTH_SHORT).show();
                                        showList();
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

        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,
                                           int position, long arg3) {
                String search = (String)adapterView.getItemAtPosition(position);
                removeItemFromList(search);
                return true;
            }
        });
        dbHelper.close();
    }

    // method to remove list item
    protected void removeItemFromList(final String search) {

        AlertDialog.Builder alert = new AlertDialog.Builder(NewMakanan.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub

                // main code on after clicking yes
                dbHelper.deleteSearchFood(search);
                showList();
            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();

    }
}

