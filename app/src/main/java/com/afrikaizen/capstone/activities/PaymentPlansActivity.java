package com.afrikaizen.capstone.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.controllers.PaymentPlansDetailFragment;
import com.afrikaizen.capstone.controllers.PaymentPlansFragment;
import com.afrikaizen.capstone.models.NewActivity;
import com.afrikaizen.capstone.models.PaymentPlan;
import com.afrikaizen.capstone.singleton.AppBus;
import com.squareup.otto.Subscribe;

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

        switchFragments(new NewActivity(0));
    }

    @Subscribe
    public void switchFragments(NewActivity a){
        Fragment f = null;
        switch(a.getActivityNumber()){
            case 0:
                f = new PaymentPlansFragment();
                break;
            case 1:
                f = new PaymentPlansDetailFragment();
                break;
        }
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, f);
        fragmentTransaction.commit();
    }
}
