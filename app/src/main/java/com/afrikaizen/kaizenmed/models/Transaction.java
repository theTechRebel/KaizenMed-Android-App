package com.afrikaizen.kaizenmed.models;

/**
 * Created by Steve on 21/3/2016.
 */
public class Transaction {
    String time;
    String paymentType;
    String date;
    String customerDetails;
    String amount;
    String details;
    String confirmaionCode;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(String customerDetails) {
        this.customerDetails = customerDetails;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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
