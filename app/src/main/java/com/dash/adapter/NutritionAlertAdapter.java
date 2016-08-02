package com.dash.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;

import com.dash.model.Menu;

/**
 * Created by Hans CK on 18-Jul-16.
 */

public class NutritionAlertAdapter {

    private Activity activity;

    public NutritionAlertAdapter(Activity act, Menu menu) {
        this.activity = act;
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);
        builderSingle.setIcon(menu.getImage());
        builderSingle.setTitle(menu.getFood());

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                activity, android.R.layout.simple_list_item_1);
        arrayAdapter.add("Berat : " + menu.getWeight() + " gram");
        arrayAdapter.add("Karbohidrat : " + menu.getCarbs() + " gram");
        arrayAdapter.add("Lemak : " + menu.getFat() + " gram");
        arrayAdapter.add("Protein : " + menu.getProtein() + " gram");
        arrayAdapter.add("Kalori : " + menu.getCalory() + " kkal" );
        arrayAdapter.add("Kolesterol : " + menu.getChol() + " mg");
        arrayAdapter.add("Sodium : " + menu.getSodium() + " mg");
        arrayAdapter.add("Kalium : " + menu.getPotassium() + " mg");
        arrayAdapter.add("Kalsium : " + menu.getCalcium() + " mg" );

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
