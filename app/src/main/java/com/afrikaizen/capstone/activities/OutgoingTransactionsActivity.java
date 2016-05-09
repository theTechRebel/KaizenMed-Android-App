package com.afrikaizen.capstone.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.controllers.OutgoingTransactionFragment;

/**
 * Created by Steve on 8/5/2016.
 */
public class OutgoingTransactionsActivity extends AppActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpActivity();

        Bundle extras = getIntent().getExtras();
        if(extras.getInt("TYPE")!=0){
            int id = extras.getInt("TYPE");
            OutgoingTransactionFragment out = new OutgoingTransactionFragment();
            switch(id){
                case R.id.send:
                    out.setOutGoingPaymentType("Payout");
                    break;
                case R.id.refund:
                    out.setOutGoingPaymentType("Refund");
                    break;
                case R.id.pay:
                    out.setOutGoingPaymentType("Bill");
                    break;
            }
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, out);
            fragmentTransaction.commit();

        }
    }
}
