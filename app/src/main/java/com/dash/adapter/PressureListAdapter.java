package com.dash.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dash.dietdash.R;
import com.dash.model.Pressure;

import java.util.ArrayList;

/**
 * Created by Hans CK on 30-Jul-16.
 */

public class PressureListAdapter extends BaseAdapter {
    private ArrayList<Pressure> listPressure;
    private Context context;
    private LayoutInflater inflater;

    public PressureListAdapter(Context context, ArrayList<Pressure> listPressure) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listPressure = listPressure;
    }

    @Override
    public int getCount() {
        return listPressure.size();
    }

    @Override
    public Object getItem(int position) {
        return listPressure.get(position);
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
            view = inflater.inflate(R.layout.diary_list_item, parent, false);
            holder = new Holder();
            assert view != null;

            holder.txtPressure = (TextView) view.findViewById(R.id.txtPressure);
            holder.txtPressurePrev = (TextView) view.findViewById(R.id.txtPressurePrev);
            holder.txtInfo = (TextView) view.findViewById(R.id.txtInfo);
            holder.imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.txtPressurePrev.setText(listPressure.get(position).getDatePrev() + "  :  " + listPressure.get(position).getSystolicPrev() + "/" + listPressure.get(position).getDiastolicPrev());
        holder.txtPressure.setText(listPressure.get(position).getDate() + "  :  " + listPressure.get(position).getSystolic() + "/" + listPressure.get(position).getDiastolic());
        holder.txtInfo.setText("NOTE : " + listPressure.get(position).getInfo());
        if(listPressure.get(position).getInfo().equals("NAIK")){
            holder.txtInfo.setTextColor(Color.RED);
            holder.imgIcon.setImageResource(R.drawable.face_worried);
        } else{
            holder.txtInfo.setTextColor(Color.BLUE);
            holder.imgIcon.setImageResource(R.drawable.face_smile);
        }
        return view;
    }

    static class Holder {
        TextView txtPressure;
        TextView txtPressurePrev;
        TextView txtInfo;
        ImageView imgIcon;
    }
}


