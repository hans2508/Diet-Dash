package com.dash.dietdash;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class InputMenuDetail extends AppCompatActivity {

    public static InputMenuDetail instance;
    private InputMenuInfo inputMenuInfo;
    private InputMenuMorning inputMenuMorning;
    private InputMenuLunch inputMenuLunch;
    private InputMenuDinner inputMenuDinner;
    private TabLayout allTabs;
    private String date;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        instance=this;
        getAllWidgets();
        bindWidgetsWithAnEvent();
        setupTabLayout();
    }
    public static InputMenuDetail getInstance() {
        return instance;
    }
    private void getAllWidgets() {
        allTabs = (TabLayout) findViewById(R.id.tabs);
    }
    private void setupTabLayout() {
        inputMenuInfo = new InputMenuInfo();
        inputMenuMorning = new InputMenuMorning();
        inputMenuLunch = new InputMenuLunch();
        inputMenuDinner = new InputMenuDinner();

        allTabs.addTab(allTabs.newTab().setText("INFORMASI"),true);
        allTabs.addTab(allTabs.newTab().setText("SARAPAN"));
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
                bundle.putString("date", date);
                inputMenuInfo.setArguments(bundle);
                replaceFragment(inputMenuInfo);
                break;
            case 1 :
                bundle = new Bundle();
                bundle.putString("date", date);
                inputMenuMorning.setArguments(bundle);
                replaceFragment(inputMenuMorning);
                break;
            case 2 :
                bundle = new Bundle();
                bundle.putString("date", date);
                inputMenuLunch.setArguments(bundle);
                replaceFragment(inputMenuLunch);
                break;
            case 3 :
                bundle = new Bundle();
                bundle.putString("date", date);
                inputMenuDinner.setArguments(bundle);
                replaceFragment(inputMenuDinner);
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
