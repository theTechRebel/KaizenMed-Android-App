package com.afrikaizen.capstone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.controllers.TransactionsFragment;
import com.afrikaizen.capstone.imports.SlidingTabLayout;
import com.afrikaizen.capstone.singleton.AppPreferences;

/**
 * Created by Steve on 07/08/2015.
 */
public class TransactionsActivity extends Inject {

    //UI elements
    private ViewPager pager;
    private SlidingTabLayout tabs;

    //Defining Variables
    String[] tabTitles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        activity = "transactions";
        setUpActivity();

        tabTitles = new String[2];

        if (AppPreferences.getInstance(this).getEcoCashWallet() != AppPreferences.getInstance(this).DEFAULT_VALUE_STRING) {
            tabTitles[0] = "EcoCash " + AppPreferences.getInstance(this).getEcoCashWallet();
            if (AppPreferences.getInstance(this).getTeleCashWallet() != AppPreferences.getInstance(this).DEFAULT_VALUE_STRING) {
                tabTitles[1] = "TeleCash " + AppPreferences.getInstance(this).getTeleCashWallet();
            }
        } else if (AppPreferences.getInstance(this).getTeleCashWallet() != AppPreferences.getInstance(this).DEFAULT_VALUE_STRING) {
            tabTitles[0] = "TeleCash " + AppPreferences.getInstance(this).getTeleCashWallet();
            if (AppPreferences.getInstance(this).getEcoCashWallet() != AppPreferences.getInstance(this).DEFAULT_VALUE_STRING) {
                tabTitles[1] = "EcoCash " + AppPreferences.getInstance(this).getEcoCashWallet();
            }
        }


        //intialize tabs
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setBackgroundColor(getResources().getColor(R.color.PrimaryColor));
        tabs.setSelectedIndicatorColors(getResources().getColor(R.color.divider));
        tabs.setViewPager(pager);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String item = extras.getString("item");
            Log.d("item",item);
            switch (item){
                case "incoming":
                    onNavigationItemSelected(navigationMenu.findItem(R.id.incoming));
                    break;
                case "outgoing":
                    onNavigationItemSelected(navigationMenu.findItem(R.id.outgoing));
                    break;
                default:
                    onNavigationItemSelected(navigationMenu.findItem(R.id.status));
                    break;
            }
        }else{
            onNavigationItemSelected(navigationMenu.findItem(R.id.status));
            Log.d("item","No Bundle");
        }
    }

    class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }
        Fragment fragment = null;
        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    fragment = new TransactionsFragment();
                    break;
                case 1:
                    fragment = new TransactionsFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}