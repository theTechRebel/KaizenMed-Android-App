package com.afrikaizen.capstone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.controllers.AccountFragment;
import com.afrikaizen.capstone.controllers.PaymentHistoryFragment;
import com.afrikaizen.capstone.models.Account;
import com.afrikaizen.capstone.models.Target;
import com.afrikaizen.capstone.models.Transaction;
import com.afrikaizen.capstone.orm.RealmService;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Steve on 23/4/2016.
 */
public class PaymentHistoryActivity extends AppActivity{
    private DrawerLayout drawerLayout;
    Realm db;
    Transaction t = null;
    int id = 0;
    String phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = "paymentHistory";
        this.db = RealmService.getInstance(getApplication()).getRealm();
        setContentView(R.layout.activity_main);
        setUpActivity();

        Bundle extras = getIntent().getExtras();
        if(extras.getString("TARGET_CUSTOMER")!=null){
            id = extras.getInt("TARGET_ID");
            phone = extras.getString("TARGET_CUSTOMER");

            Target t = db.where(Target.class)
                    .equalTo("id",id)
                    .findFirst();

            Account a = db.where(Account.class)
                    .equalTo("phone",phone)
                    .findFirst();

            this.t = db.where(Transaction.class)
                    .equalTo("target.id",t.getId())
                    .equalTo("phoneNumber",phone)
                    .findFirst();
`
            if(this.t == null){
                Toast.makeText(this,"No payments have been made yet for this plan",Toast.LENGTH_LONG);
                Intent i = new Intent();
                Intent intent = new Intent(this, AccountActivity.class);
                startActivity(intent);
                finish();
                return;
            }

            //RealmQuery<Target> query = db.where(Target.class)
                    //.equalTo("id",id);
            //Target target = query.findFirst();
            //t = target.getPlan();

        }else{
            id = extras.getInt("TRANSACTION_ID");
            RealmQuery<Transaction> query = db.where(Transaction.class)
                    .equalTo("id",id);
            t = query.findFirst();
        }

        PaymentHistoryFragment f = new PaymentHistoryFragment();
        f.setTransaction(t);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, f);
        fragmentTransaction.commit();


    }
}
