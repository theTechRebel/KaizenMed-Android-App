package com.afrikaizen.capstone.models;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by Steve on 21/3/2016.
 */
public class Transaction extends RealmObject{
    String paymentType;
    Date date;
    String customerDetails;
    Double amount;
    String details;
    String confirmaionCode;


    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(String customerDetails) {
        this.customerDetails = customerDetails;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getConfirmaionCode() {
        return confirmaionCode;
    }

    public void setConfirmaionCode(String confirmaionCode) {
        this.confirmaionCode = confirmaionCode;
    }
}
