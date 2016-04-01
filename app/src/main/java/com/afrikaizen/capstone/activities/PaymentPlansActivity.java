package com.afrikaizen.capstone.activities;

import android.os.Bundle;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.controllers.PaymentPlansFragment;

/**
 * Created by Steve on 22/3/2016.
 */
public class PaymentPlansActivity extends AppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = "paymentplans";
        setContentView(R.layout.activity_main);
        setUpActivity();

        PaymentPlansFragment f = new PaymentPlansFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, f);
        fragmentTransaction.commit();

        //onNavigationItemSelected(navigationMenu.findItem(R.id.inventory));
    }
}
