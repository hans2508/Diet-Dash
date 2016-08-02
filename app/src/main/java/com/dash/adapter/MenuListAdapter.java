package com.dash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dash.dietdash.R;
import com.dash.model.Menu;

import java.util.ArrayList;

/**
 * Created by Hans CK on 18-Jul-16.
 */

public class MenuListAdapter extends BaseAdapter {
    private ArrayList<Menu> listMenu;
    private Context context;
    private LayoutInflater inflater;

    public MenuListAdapter(Context context, ArrayList<Menu> listMenu) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listMenu = listMenu;
    }

    @Override
    public int getCount() {
        return listMenu.size();
    }

    @Override
    public Object getItem(int position) {
        return listMenu.get(position);
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
        holder.txtFood.setText(listMenu.get(position).getFood());
        holder.txtURT.setText(listMenu.get(position).getUrt());
        holder.imgIcon.setImageResource(listMenu.get(position).getImage());
        return view;
    }

    static class Holder {
        ImageView imgIcon;
        TextView txtFood;
        TextView txtURT;
    }
}
