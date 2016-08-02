package com.dash.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;

import com.dash.model.Makanan;

import java.util.ArrayList;

/**
 * Created by Hans CK on 23-Jul-16.
 */

public class MakananAlertAdapter {

    private Activity activity;

    public MakananAlertAdapter(Activity act, ArrayList<Makanan> listFood) {
        this.activity = act;
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);
        builderSingle.setTitle("Pilih Menu Makanan :");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                activity, android.R.layout.simple_list_item_1);
        for (int i = 0; i < listFood.size(); i++) {
            arrayAdapter.add(listFood.get(i).getFood());
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
                        String strName = arrayAdapter.getItem(which);
                    }
                });
        builderSingle.show();
    }
}
