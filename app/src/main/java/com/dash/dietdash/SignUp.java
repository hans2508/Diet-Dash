package com.dash.dietdash;

import android.content.Context;
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

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void profile(View view) {
        String email = ((EditText) findViewById(R.id.editEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.editPass)).getText().toString();
        String confPass = ((EditText) findViewById(R.id.editConfPass)).getText().toString();

        if ((!email.equals("") && !password.equals("") && !confPass.equals(""))) {
            if (confPass.equals(password)) {
                DataBaseHelper dbHelper = new DataBaseHelper(this);
                dbHelper.openDataBase();
                boolean status = true;

                ArrayList<User> listUser = new ArrayList<>();
                listUser = dbHelper.getUser();
                for (int i = 0; i < listUser.size(); i++) {
                    if (listUser.get(i).getEmail().equals(email)) {
                        status = false;
                    }
                }
                if (status == true) {
                    User user = new User(email, password);
                    dbHelper.addUser(user);

                    SharedPreferences userDetails = getSharedPreferences("dietPrefs", Context.MODE_PRIVATE);
                    Editor editor = userDetails.edit();
                    editor.putString("emailKey", email);
                    editor.commit();

                    Intent intent = new Intent(SignUp.this, Profile.class);
                    intent.putExtra("userEmail", email);
                    intent.putExtra("userPassword", password);
                    startActivity(intent);
                    Toast.makeText(getBaseContext(), "Your Personal Account created!", Toast.LENGTH_SHORT).show();
                    dbHelper.close();
                } else{
                    Toast.makeText(getBaseContext(), "Email has already been used!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getBaseContext(), "Password and Confirm Password not match!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getBaseContext(), "Please fill in first!", Toast.LENGTH_SHORT).show();
        }
    }


}
