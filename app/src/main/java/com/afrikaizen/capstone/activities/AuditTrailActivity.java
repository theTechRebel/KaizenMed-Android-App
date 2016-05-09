package com.afrikaizen.capstone.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.controllers.AuditTrailFragment;
import com.afrikaizen.capstone.controllers.StatusFragmentPieChart;

/**
 * Created by Steve on 9/5/2016.
 */
public class AuditTrailActivity extends AppActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        activity = "audit";
        setUpActivity();


        AuditTrailFragment f = new AuditTrailFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, f);
        fragmentTransaction.commit();
    }
}
