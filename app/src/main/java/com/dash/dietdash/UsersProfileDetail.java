package com.dash.dietdash;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class UsersProfileDetail extends AppCompatActivity {

    public static UsersProfileDetail instance;
    private UsersProfilePressure usersProfilePressure;
    private UsersProfileList usersProfileList;
    private TabLayout allTabs;
    private int day;
    private String email;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        Intent intent = getIntent();
        day = Integer.parseInt(intent.getStringExtra("sentDay"));
        email = intent.getStringExtra("email");
        instance=this;
        getAllWidgets();
        bindWidgetsWithAnEvent();
        setupTabLayout();
    }
    public static UsersProfileDetail getInstance() {
        return instance;
    }
    private void getAllWidgets() {
        allTabs = (TabLayout) findViewById(R.id.tabs);
    }
    private void setupTabLayout() {
        usersProfilePressure = new UsersProfilePressure();
        usersProfileList = new UsersProfileList();

        allTabs.addTab(allTabs.newTab().setText("PRESSURE"),true);
        allTabs.addTab(allTabs.newTab().setText("LIST FOOD"));
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
                bundle.putInt("day", day);
                bundle.putString("email", email);
                usersProfilePressure.setArguments(bundle);
                replaceFragment(usersProfilePressure);
                break;
            case 1 :
                bundle = new Bundle();
                bundle.putInt("day", day);
                bundle.putString("email", email);
                usersProfileList.setArguments(bundle);
                replaceFragment(usersProfileList);
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
