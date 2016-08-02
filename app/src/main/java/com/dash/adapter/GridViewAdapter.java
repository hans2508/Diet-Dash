package com.dash.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.dash.dietdash.R;

import java.util.ArrayList;

import com.dash.model.Day;

/**
 * Created by Hans CK on 16-Jul-16.
 */

public class GridViewAdapter extends ArrayAdapter<Day> {
    Context context;
    int layoutResourceId;
    ArrayList<Day> data = new ArrayList<Day>();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<Day> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecordHolder();
            holder.image = (ImageView) row.findViewById(R.id.grid_image);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        //Guest guest = data.get(position);
        holder.image.setImageResource(data.get(position).getImage());
        return row;

    }

    static class RecordHolder {
        ImageView image;

    }
}
