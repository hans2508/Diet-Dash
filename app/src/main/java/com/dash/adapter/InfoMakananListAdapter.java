package com.dash.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dash.dietdash.R;
import com.dash.model.InfoMakanan;

import java.util.ArrayList;

/**
 * Created by Hans CK on 28-Jul-16.
 */

public class InfoMakananListAdapter extends BaseAdapter {
    private ArrayList<InfoMakanan> listInfo;
    private Context context;
    private LayoutInflater inflater;

    public InfoMakananListAdapter(Context context, ArrayList<InfoMakanan> listInfo) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listInfo = listInfo;
    }

    @Override
    public int getCount() {
        return listInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return listInfo.get(position);
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
            view = inflater.inflate(R.layout.makanan_list_item, parent, false);
            holder = new Holder();
            assert view != null;

            holder.txtIndikator = (TextView) view.findViewById(R.id.txtIndikator);
            holder.txtInfo = (TextView) view.findViewById(R.id.txtInfo);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.txtIndikator.setText(listInfo.get(position).getIndikator());
        holder.txtInfo.setText(listInfo.get(position).getInfo());
        if (listInfo.get(position).getStatus() == 1) {
            holder.txtInfo.setTextColor(Color.RED);
        }
        return view;
    }

    static class Holder {
        TextView txtIndikator;
        TextView txtInfo;
    }
}


