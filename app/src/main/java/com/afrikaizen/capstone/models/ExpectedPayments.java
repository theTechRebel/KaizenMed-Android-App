package com.afrikaizen.capstone.models;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Steve on 19/4/2016.
 */
public class ExpectedPayments extends RealmObject{
    @PrimaryKey
    int id;
    RealmList<Account> accounts;

    public RealmList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(RealmList<Account> accounts) {
        this.accounts = accounts;
    }

    public int getI() {
        return id;
    }

    public void setI(int id) {
        this.id = id;
    }
}
