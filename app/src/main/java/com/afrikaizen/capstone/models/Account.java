package com.afrikaizen.capstone.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Steve on 31/3/2016.
 */
public class Account extends RealmObject{
    String name;
    @Ignore
    String surname;
    @PrimaryKey
    String phone;
    @Ignore
    String email;
    private RealmList<Target> targets;
    private RealmList<Transaction> transactions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RealmList<Target> getTargets() {
        return targets;
    }

    public void setTargets(RealmList<Target> targets) {
        this.targets = targets;
    }

    public RealmList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(RealmList<Transaction> transactions) {
        this.transactions = transactions;
    }
}
