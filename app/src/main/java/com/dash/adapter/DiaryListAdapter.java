package com.dash.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dash.dietdash.R;
import com.dash.model.Pressure;

/**
 * Created by Hans CK on 16-Jul-16.
 */

public class DiaryListAdapter extends ArrayAdapter<Pressure> {

    Context context;
    int layoutResourceId;
    Pressure pressure[] = null;

    public DiaryListAdapter(Context context, int layoutResourceId, Pressure[] pres) {
        super(context, layoutResourceId, pres);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.pressure = pres;
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
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtPressure = (TextView)row.findViewById(R.id.txtPressure);
            holder.txtInfo = (TextView)row.findViewById(R.id.txtInfo);

            row.setTag(holder);
        }
        else
        {
            holder = (EventHolder)row.getTag();
        }

        Pressure p = pressure[position];
        holder.txtPressure.setText("PRESSURE : " + p.getSystolic() + "/" + p.getDiastolic());
        holder.txtInfo.setText("-> " + p.getInfo());
        if(p.getInfo().equals("NAIK")){
            holder.txtInfo.setTextColor(Color.RED);
        }
        holder.imgIcon.setImageResource(p.getImage());

        return row;
    }

    static class EventHolder
    {
        ImageView imgIcon;
        TextView txtPressure;
        TextView txtInfo;
    }
}

