package com.afrikaizen.capstone.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.controllers.OutgoingTransactionsListFragment;
import com.afrikaizen.capstone.controllers.TransactionFragment;
import com.afrikaizen.capstone.imports.SlidingTabLayout;
import com.afrikaizen.capstone.singleton.AppPreferences;

/**
 * Created by Steve on 9/5/2016.
 */
public class OutgoingTransactionsListActivity extends AppActivity {

    //UI elements
    private ViewPager pager;
    private SlidingTabLayout tabs;

    //Defining Variables
    String[] tabTitles;
    String transactionType;


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
                    transactionType = "Incoming Payment";
                    break;
                case "outgoing":
                    transactionType = "Outgoing Payment";
                    break;
            }
        }
    }

    class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int i) {
            OutgoingTransactionsListFragment t = null;
            switch (i) {
                case 0:
                    Bundle b1 = new Bundle();
                    b1.putString("WALLET","ecocash");
                    b1.putString("TRANSACTION",transactionType);
                    OutgoingTransactionsListFragment fragment1 = new OutgoingTransactionsListFragment();
                    fragment1.setArguments(b1);
                    t = fragment1;
                    break;
                case 1:
                    Bundle b2 = new Bundle();
                    b2.putString("WALLET","telecash");
                    b2.putString("TRANSACTION",transactionType);
                    OutgoingTransactionsListFragment fragment2 = new OutgoingTransactionsListFragment();
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