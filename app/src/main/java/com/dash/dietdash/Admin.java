package com.dash.dietdash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    public void insertNewMakanan(View view)
    {
        Intent intent = new Intent(Admin.this, NewMakanan.class);
        startActivity(intent);
    }

    public void usersProfile(View view)
    {
        Intent intent = new Intent(Admin.this, UsersProfile.class);
        startActivity(intent);
    }

    public void menu_logout_admin(View view)
    {
        Intent intent = new Intent(Admin.this, Login.class);
        startActivity(intent);
    }
}
