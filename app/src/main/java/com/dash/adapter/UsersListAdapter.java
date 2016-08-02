package com.dash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dash.dietdash.R;
import com.dash.model.User;

import java.util.ArrayList;

/**
 * Created by Hans CK on 30-Jul-16.
 */

public class UsersListAdapter extends BaseAdapter {
    private ArrayList<User> listUsers;
    private Context context;
    private LayoutInflater inflater;

    public UsersListAdapter(Context context, ArrayList<User> listUsers) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listUsers = listUsers;
    }

    @Override
    public int getCount() {
        return listUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return listUsers.get(position);
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
            view = inflater.inflate(R.layout.users_list_item, parent, false);
            holder = new Holder();
            assert view != null;

            holder.txtName = (TextView) view.findViewById(R.id.txtName);
            holder.txtAge = (TextView) view.findViewById(R.id.txtAge);
            holder.imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        if (!listUsers.get(position).getEmail().equals("admin")) {
            holder.txtName.setText(listUsers.get(position).getName());
            holder.txtAge.setText("Age : " + listUsers.get(position).getAge());
            if(listUsers.get(position).getGender()==1) {
                holder.imgIcon.setImageResource(R.drawable.user_male);
            } else {
                holder.imgIcon.setImageResource(R.drawable.user_female);
            }
        }
        return view;
    }

    static class Holder {
        TextView txtName;
        TextView txtAge;
        ImageView imgIcon;
    }
}
