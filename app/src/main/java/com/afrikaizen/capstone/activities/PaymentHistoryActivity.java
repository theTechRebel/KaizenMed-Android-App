package com.afrikaizen.capstone.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.controllers.AccountFragment;
import com.afrikaizen.capstone.controllers.PaymentHistoryFragment;
import com.afrikaizen.capstone.models.Transaction;
import com.afrikaizen.capstone.orm.RealmService;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Steve on 23/4/2016.
 */
public class PaymentHistoryActivity extends AppActivity{
    private DrawerLayout drawerLayout;
    Realm db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = "paymentHistory";
        this.db = RealmService.getInstance(getApplication()).getRealm();
        setContentView(R.layout.activity_main);
        setUpActivity();

        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("TRANSACTION_ID");

        RealmQuery<Transaction> query = db.where(Transaction.class)
                                .equalTo("id",id);
        Transaction t = query.findFirst();

        PaymentHistoryFragment f = new PaymentHistoryFragment();
        f.setTransaction(t);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, f);
        fragmentTransaction.commit();

    }
}
