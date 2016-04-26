package com.afrikaizen.capstone.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.controllers.StatusFragmentBarChart;
import com.afrikaizen.capstone.controllers.StatusFragmentPieChart;
import com.afrikaizen.capstone.controllers.TransactionFragment;
import com.afrikaizen.capstone.imports.SlidingTabLayout;
import com.afrikaizen.capstone.orm.RealmService;

/**
 * Created by Steve on 24/4/2016.
 */
public class StatusActivity extends AppActivity {
    //UI elements
    private ViewPager pager;
    private SlidingTabLayout tabs;

    //Defining Variables
    String[] tabTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        activity = "status";
        setUpActivity();

        tabTitles = new String[2];
        tabTitles[0] = "Wallet Balance";
        tabTitles[1] = "Transaction Summary";

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
            Fragment t = null;
            switch (i) {
                case 0:
                    StatusFragmentPieChart fragment1 = new StatusFragmentPieChart();
                    t = fragment1;
                    break;
                case 1:
                    StatusFragmentBarChart fragment2 = new StatusFragmentBarChart();
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
