package com.afrikaizen.capstone.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.controllers.InvoicesFragment;
import com.afrikaizen.capstone.controllers.StatusFragmentPieChart;
import com.afrikaizen.capstone.controllers.TransactionFragment;
import com.afrikaizen.capstone.imports.SlidingTabLayout;
import com.afrikaizen.capstone.singleton.AppPreferences;

/**
 * Created by Steve on 8/5/2016.
 */
public class InvoicesActivity extends AppActivity {

    //UI elements
    private ViewPager pager;
    private SlidingTabLayout tabs;

    //Defining Variables
    String[] tabTitles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        activity = "invoices";
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
    }

    class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int i) {
            InvoicesFragment t = null;
            switch (i) {
                case 0:
                    Bundle b1 = new Bundle();
                    b1.putString("WALLET","ecocash");
                    InvoicesFragment fragment1 = new InvoicesFragment();
                    fragment1.setArguments(b1);
                    t = fragment1;
                    break;
                case 1:
                    Bundle b2 = new Bundle();
                    b2.putString("WALLET","telecash");
                    InvoicesFragment fragment2 = new InvoicesFragment();
                    fragment2.setArguments(b2);
                    t = fragment2;
                    break;
            }
            return t;
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