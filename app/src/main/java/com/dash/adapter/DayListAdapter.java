package com.dash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dash.dietdash.R;

import java.util.ArrayList;

/**
 * Created by Hans CK on 05-Aug-16.
 */

public class DayListAdapter extends BaseAdapter{
    private ArrayList<String> listDay;
    private Context context;
    private LayoutInflater inflater;

    public DayListAdapter(Context context, ArrayList<String> listDay) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listDay = listDay;
    }

    @Override
    public int getCount() {
        return listDay.size();
    }

    @Override
    public Object getItem(int position) {
        return listDay.get(position);
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
            view = inflater.inflate(R.layout.new_makanan_list_item, parent, false);
            holder = new Holder();
            assert view != null;

            holder.txtSearch = (TextView) view.findViewById(R.id.txtSearch);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        int index = position + 1;
        holder.txtSearch.setText(index + ". " + listDay.get(position));
        return view;
    }

    static class Holder {
        TextView txtSearch;
    }
}


