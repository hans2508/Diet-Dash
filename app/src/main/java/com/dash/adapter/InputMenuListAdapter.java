package com.dash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dash.dietdash.R;
import com.dash.model.Makanan;

import java.util.ArrayList;

/**
 * Created by Hans CK on 28-Jul-16.
 */

public class InputMenuListAdapter extends BaseAdapter {
    private ArrayList<Makanan> listMakanan;
    private Context context;
    private LayoutInflater inflater;

    public InputMenuListAdapter(Context context, ArrayList<Makanan> listMakanan) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listMakanan = listMakanan;
    }

    @Override
    public int getCount() {
        return listMakanan.size();
    }

    @Override
    public Object getItem(int position) {
        return listMakanan.get(position);
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
            view = inflater.inflate(R.layout.menu_list_item, parent, false);
            holder = new Holder();
            assert view != null;

            holder.imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
            holder.txtFood = (TextView) view.findViewById(R.id.txtFood);
            holder.txtURT = (TextView) view.findViewById(R.id.txtURT);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.txtFood.setText(listMakanan.get(position).getFood());
        holder.txtURT.setText(listMakanan.get(position).getUrt());
        holder.imgIcon.setImageResource(R.drawable.food);
        return view;
    }

    static class Holder {
        ImageView imgIcon;
        TextView txtFood;
        TextView txtURT;
    }
}

