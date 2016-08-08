package com.dash.dietdash;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class DetailSchedule extends AppCompatActivity {

    public static DetailSchedule instance;
    private DetailNutrition detailNutrition;
    private DetailNutritionHistory detailNutritionHistory;
    private DetailBreakfast detailBreakfast;
    private DetailFruitMorning detailFruitMorning;
    private DetailLunch detailLunch;
    private DetailFruitEvening detailFruitEvening;
    private DetailDinner detailDinner;
    private DetailSnack detailSnack;
    private TabLayout allTabs;
    private int day, status;
    private Bundle bundle;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        Intent intent = getIntent();
        day = Integer.parseInt(intent.getStringExtra("sentDay"));
        status = Integer.parseInt(intent.getStringExtra("status"));
        date = intent.getStringExtra("date");
        instance=this;
        getAllWidgets();
        bindWidgetsWithAnEvent();
        setupTabLayout();
    }
    public static DetailSchedule getInstance() {
        return instance;
    }
    private void getAllWidgets() {
        allTabs = (TabLayout) findViewById(R.id.tabs);
    }
    private void setupTabLayout() {
        detailNutritionHistory = new DetailNutritionHistory();
        detailNutrition = new DetailNutrition();
        detailBreakfast = new DetailBreakfast();
        detailFruitMorning = new DetailFruitMorning();
        detailLunch = new DetailLunch();
        detailFruitEvening = new DetailFruitEvening();
        detailDinner = new DetailDinner();
        detailSnack = new DetailSnack();

        allTabs.addTab(allTabs.newTab().setText("1"),true);
        allTabs.addTab(allTabs.newTab().setText("2"));
        allTabs.addTab(allTabs.newTab().setText("3"));
        allTabs.addTab(allTabs.newTab().setText("4"));
        allTabs.addTab(allTabs.newTab().setText("5"));
        allTabs.addTab(allTabs.newTab().setText("6"));
        allTabs.addTab(allTabs.newTab().setText("7"));
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
                bundle.putString("date", date);
                if(status == 1) {
                    detailNutritionHistory.setArguments(bundle);
                    replaceFragment(detailNutritionHistory);
                } else {
                    detailNutrition.setArguments(bundle);
                    replaceFragment(detailNutrition);
                }
                break;
            case 1 :
                bundle = new Bundle();
                bundle.putInt("day", day);
                detailBreakfast.setArguments(bundle);
                replaceFragment(detailBreakfast);
                break;
            case 2 :
                bundle = new Bundle();
                bundle.putInt("day", day);
                detailFruitMorning.setArguments(bundle);
                replaceFragment(detailFruitMorning);
                break;
            case 3 :
                bundle = new Bundle();
                bundle.putInt("day", day);
                detailLunch.setArguments(bundle);
                replaceFragment(detailLunch);
                break;
            case 4 :
                bundle = new Bundle();
                bundle.putInt("day", day);
                detailFruitEvening.setArguments(bundle);
                replaceFragment(detailFruitEvening);
                break;
            case 5 :
                bundle = new Bundle();
                bundle.putInt("day", day);
                detailDinner.setArguments(bundle);
                replaceFragment(detailDinner);
                break;
            case 6 :
                bundle = new Bundle();
                bundle.putInt("day", day);
                detailSnack.setArguments(bundle);
                replaceFragment(detailSnack);
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
