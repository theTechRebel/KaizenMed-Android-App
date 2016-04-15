package com.afrikaizen.capstone.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Steve on 15/4/2016.
 */
public class Target extends RealmObject {

    @PrimaryKey
    int id;
    int quantity;
    PaymentPlan plan;
    Account customer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PaymentPlan getPlan() {
        return plan;
    }

    public void setPlan(PaymentPlan plan) {
        this.plan = plan;
    }

    public Account getCustomer() {
        return customer;
    }

    public void setCustomer(Account customer) {
        this.customer = customer;
    }
}
