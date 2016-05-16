package com.afrikaizen.capstone.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.afrikaizen.capstone.models.Account;
import com.afrikaizen.capstone.models.PaymentPlan;
import com.afrikaizen.capstone.models.Target;
import com.afrikaizen.capstone.models.Transaction;
import com.afrikaizen.capstone.models.Wallet;
import com.afrikaizen.capstone.orm.RealmService;
import com.afrikaizen.capstone.rest.ApiService;

import java.util.ArrayList;
import java.util.Arrays;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Steve on 10/5/2016.
 */
public class SyncService extends Service{

    Realm db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = RealmService.getInstance(getApplication()).getRealm();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("API","Data transmission has stopped");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sendAccounts();
        sendInvoices();
        sendPayments();
        sendStats();
        return START_STICKY;
    }

    private void sendAccounts(){
        RealmResults<Account> accountsList = db.where(Account.class).findAll();
        ArrayList<Account> data =
                new ArrayList<Account>(Arrays.<Account>asList());
        data.addAll(accountsList);

        for (Account a: data) {
            ApiService.getInstance().account(a);
        }
    }

    private void sendInvoices(){
        RealmResults<Target> targetList = db.where(Target.class).findAll();
        ArrayList<Target> data =
                new ArrayList<Target>(Arrays.<Target>asList());
        data.addAll(targetList);

        for (Target t: data) {
            Double amount = t.getPlan().getAmount();
            String description = t.getPlan().getDescription();
            ApiService.getInstance().invoices(t,amount,description);
        }
    }


    private void sendPayments(){
        RealmResults<Transaction> transactions = db.where(Transaction.class).findAll();
        ArrayList<Transaction> data =
                new ArrayList<Transaction>(Arrays.<Transaction>asList());
        data.addAll(transactions);

        for (Transaction t: data) {
            ApiService.getInstance().sendPayments(t);
        }
    }


    private void sendStats(){
        RealmResults<Account> a = db.where(Account.class).findAll();
        int accounts = a.size();

        RealmResults<PaymentPlan> p = db.where(PaymentPlan.class).findAll();
        int plans = p.size();

        RealmResults<Transaction> t = db.where(Transaction.class).findAll();
        int trans = t.size();

        RealmResults<Wallet> listOfWallets = db.where(Wallet.class).findAll();
        Double ecocash = listOfWallets.get(0).getBalance();
        Double telecash = listOfWallets.get(1).getBalance();

        ApiService.getInstance().sendStats(ecocash,telecash,accounts,plans,trans);
    }
}
