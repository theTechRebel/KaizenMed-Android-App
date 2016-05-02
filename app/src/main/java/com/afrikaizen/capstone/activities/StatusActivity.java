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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        activity = "status";
        setUpActivity();


        StatusFragmentPieChart f = new StatusFragmentPieChart();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, f);
        fragmentTransaction.commit();
    }
}
