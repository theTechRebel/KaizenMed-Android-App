package com.afrikaizen.capstone.models;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Steve on 19/4/2016.
 */
public class ExpectedPayments extends RealmObject{
    RealmList<Account> accounts;

    public RealmList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(RealmList<Account> accounts) {
        this.accounts = accounts;
    }
}
