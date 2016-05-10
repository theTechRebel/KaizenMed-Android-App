package com.afrikaizen.capstone.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.afrikaizen.capstone.models.Account;
import com.afrikaizen.capstone.models.Target;
import com.afrikaizen.capstone.models.Transaction;
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
    @Override
    public void onCreate() {
        super.onCreate();
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
        Realm db = RealmService.getInstance(getApplication()).getRealm();
        RealmResults<Transaction> transactionList = db.where(Transaction.class).findAll();
        ArrayList<Transaction> data =
                new ArrayList<Transaction>(Arrays.<Transaction>asList());
        data.addAll(transactionList);

        RealmResults<Account> accountsList = db.where(Account.class).findAll();
        ArrayList<Account> data2 =
                new ArrayList<Account>(Arrays.<Account>asList());
        data2.addAll(accountsList);

        RealmResults<Target> targetList = db.where(Target.class).findAll();
        ArrayList<Target> data3 =
                new ArrayList<Target>(Arrays.<Target>asList());
        data3.addAll(targetList);

        ApiService.getInstance().sync(data,data2,data3);
        Log.d("API","Data transmission has begun");
        return START_STICKY;
    }
}
