package com.dash.dietdash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dash.handler.DataBaseHelper;
import com.dash.model.User;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view)
    {
        String email = ((EditText) findViewById(R.id.editEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.editPass)).getText().toString();

        if(!email.equals("") && !password.equals("")) {
            DataBaseHelper dbHelper = new DataBaseHelper(this);
            dbHelper.openDataBase();
            ArrayList<User> listUser = new ArrayList<>();
            listUser = dbHelper.getUser();
            boolean status = false;

            for (int i = 0; i < listUser.size(); i++) {
                if (listUser.get(i).getEmail().equals(email) && listUser.get(i).getPassword().equals(password)) {
                    status = true;
                    break;
                }
            }

            if (status == true) {
                SharedPreferences userDetails = getSharedPreferences("dietPrefs", MODE_PRIVATE);
                Editor editor = userDetails.edit();
                editor.putString("emailKey", email);
                editor.commit();

                Intent intent;
                if(email.equals("admin")){
                    intent = new Intent(Login.this, Admin.class);
                } else {
                    intent = new Intent(Login.this, Main.class);
                }
                startActivity(intent);
            } else {
                Toast.makeText(getBaseContext(), "Email or Password not found!", Toast.LENGTH_SHORT).show();
            }
            dbHelper.close();
        } else{
            Toast.makeText(getBaseContext(), "Please fill in the Emai and Password!", Toast.LENGTH_SHORT).show();
        }
    }

    public void signup(View view)
    {
        Intent intent = new Intent(Login.this, SignUp.class);
        startActivity(intent);
    }
}
