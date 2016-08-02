package com.dash.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dash.dietdash.R;

/**
 * Created by Hans CK on 31-Jul-16.
 */

public class NewMakananListAdapter extends ArrayAdapter<String> {

    Context context;
    int layoutResourceId;
    String listSearch[] = null;

    public NewMakananListAdapter(Context context, int layoutResourceId, String[] listSearch) {
        super(context, layoutResourceId, listSearch);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.listSearch = listSearch;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        EventHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new EventHolder();
            holder.txtSearch = (TextView)row.findViewById(R.id.txtSearch);

            row.setTag(holder);
        }
        else
        {
            holder = (EventHolder)row.getTag();
        }

        int index = position + 1;
        holder.txtSearch.setText(index + ". " + listSearch[position]);
        return row;
    }

    static class EventHolder
    {
        TextView txtSearch;
    }
}
