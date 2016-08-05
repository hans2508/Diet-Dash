package com.dash.dietdash;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Hans CK on 05-Aug-16.
 */

public class UsersProfileListDetail extends AppCompatActivity{

    public static UsersProfileListDetail instance;
    private UsersProfileListMorning usersProfileListMorning;
    private UsersProfileListLunch usersProfileListLunch;
    private UsersProfileListDinner usersProfileListDinner;
    private TabLayout allTabs;
    private String date;
    private String email;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        date = intent.getStringExtra("date");
        System.out.println("DATE1 : " + date);
        instance=this;
        getAllWidgets();
        bindWidgetsWithAnEvent();
        setupTabLayout();
    }
    public static UsersProfileListDetail getInstance() {
        return instance;
    }
    private void getAllWidgets() {
        allTabs = (TabLayout) findViewById(R.id.tabs);
    }
    private void setupTabLayout() {
        usersProfileListMorning = new UsersProfileListMorning();
        usersProfileListLunch = new UsersProfileListLunch();
        usersProfileListDinner = new UsersProfileListDinner();

        allTabs.addTab(allTabs.newTab().setText("SARAPAN"),true);
        allTabs.addTab(allTabs.newTab().setText("MAKAN SIANG"));
        allTabs.addTab(allTabs.newTab().setText("MAKAN MALAM"));
    }
    private void bindWidgetsWithAnEvent()
    {
        allTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    private void setCurrentTabFragment(int tabPosition)
    {
        switch (tabPosition)
        {
            case 0 :
                bundle = new Bundle();
                bundle.putString("email", email);
                bundle.putString("date", date);
                usersProfileListMorning.setArguments(bundle);
                replaceFragment(usersProfileListMorning);
                break;
            case 1 :
                bundle = new Bundle();
                bundle.putString("email", email);
                bundle.putString("date", date);
                usersProfileListLunch.setArguments(bundle);
                replaceFragment(usersProfileListLunch);
                break;
            case 2 :
                bundle = new Bundle();
                bundle.putString("email", email);
                bundle.putString("date", date);
                usersProfileListDinner.setArguments(bundle);
                replaceFragment(usersProfileListDinner);
                break;
        }
    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_container, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}
