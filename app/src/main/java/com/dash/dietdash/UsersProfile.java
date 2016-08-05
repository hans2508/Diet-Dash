package com.dash.dietdash;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dash.adapter.UsersListAdapter;
import com.dash.handler.DataBaseHelper;
import com.dash.model.User;

import java.util.ArrayList;

public class UsersProfile extends AppCompatActivity {

    private ArrayList<User> listUser;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_profile);
        context = this;

        DataBaseHelper dbHelper = new DataBaseHelper(this);
        dbHelper.openDataBase();
        listUser = dbHelper.getUser();

        ListView listView = (ListView) findViewById(R.id.listUsers);
        UsersListAdapter listViewAdapter = new UsersListAdapter(this, listUser);
        View header = (View) getLayoutInflater().inflate(R.layout.list_view_header, null);
        ((TextView) header.findViewById(R.id.txtHeader)).setText("USERS DATA");
        listView.addHeaderView(header);
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                User user = (User) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(UsersProfile.this, UsersProfileDetail.class);
                intent.putExtra("email", user.getEmail());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Builder builderSingle = new Builder(UsersProfile.this);
                builderSingle.setTitle("Informasi Biodata :");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(UsersProfile.this, android.R.layout.simple_list_item_1);

                arrayAdapter.add("Name : " + listUser.get(position).getName());
                arrayAdapter.add("Email : " + listUser.get(position).getEmail());
                if (listUser.get(position).getGender() == 1) {
                    arrayAdapter.add("Gender : Male");
                } else {
                    arrayAdapter.add("Gender : Female");
                }
                arrayAdapter.add("Age : " + listUser.get(position).getAge());
                arrayAdapter.add("Weight : " + listUser.get(position).getWeight());
                arrayAdapter.add("Height : " + listUser.get(position).getHeight());
                arrayAdapter.add("Activity : " + listUser.get(position).getActivity());

                builderSingle.setNegativeButton(
                        "Done",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builderSingle.setAdapter(
                        arrayAdapter,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String strName = arrayAdapter.getItem(which);
                            }
                        });
                builderSingle.show();
                return true;
            }
        });
    }
}
