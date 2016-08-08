package com.dash.adapter;

/**
 * Created by Hans CK on 18-Jul-16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dash.dietdash.R;
import com.dash.model.Calory;

import java.util.ArrayList;

/**
 * Created by Hans CK on 23-Jul-16.
 */
public class NutritionListAdapter extends BaseAdapter {
    private ArrayList<Calory> listCal;
    private Context context;
    private LayoutInflater inflater;

    public NutritionListAdapter(Context context, ArrayList<Calory> listCal) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listCal = listCal;
    }

    @Override
    public int getCount() {
        return listCal.size();
    }

    @Override
    public Object getItem(int position) {
        return listCal.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder;
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.nutrition_list_item, parent, false);
            holder = new Holder();
            assert view != null;

            holder.imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
            holder.txtType = (TextView) view.findViewById(R.id.txtType);
            holder.txtCalory = (TextView) view.findViewById(R.id.txtCalory);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        switch (listCal.get(position).getType()){
            case "Breakfast" : holder.txtType.setText("Sarapan");
            break;
            case "Fruit Morning" : holder.txtType.setText("Buah Pagi (10:00)");
                break;
            case "Lunch" : holder.txtType.setText("Makan Siang");
                break;
            case "Fruit Evening" : holder.txtType.setText("Buah Sore (14:00)");
                break;
            case "Dinner" : holder.txtType.setText("Makan Malam");
                break;
            case "Snack" : holder.txtType.setText("Makanan Ringan");
                break;
        }
        holder.txtCalory.setText("Total callory : " + String.valueOf(listCal.get(position).getCalories()) + " kkal");
        holder.imgIcon.setImageResource(R.drawable.info);
        return view;
    }

    static class Holder {
        ImageView imgIcon;
        TextView txtType;
        TextView txtCalory;
    }
}