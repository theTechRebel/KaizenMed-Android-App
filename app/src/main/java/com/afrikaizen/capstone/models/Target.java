package com.afrikaizen.capstone.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Steve on 15/4/2016.
 */
public class Target extends RealmObject {

    @PrimaryKey
    int id;
    String paymentType;
    int quantity;
    PaymentPlan plan;
    Date dateCreated;
    Date startDate;
    Date endDate;
    Boolean requiring;
    Boolean active;
    int priority;


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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Boolean getRequiring() {
        return requiring;
    }

    public void setRequiring(Boolean requiring) {
        this.requiring = requiring;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
