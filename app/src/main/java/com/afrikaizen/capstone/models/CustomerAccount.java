package com.afrikaizen.capstone.models;

import android.app.Application;
import android.util.Log;

import com.afrikaizen.capstone.orm.RealmService;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.internal.Context;

/**
 * Created by Steve on 25/4/2016.
 */
public class CustomerAccount extends RealmObject{

    @PrimaryKey
    int id;
    Double amountLeft;
    Account account;
    Transaction transaction;
    PaymentPlan paymentPlan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getAmountLeft() {
        return amountLeft;
    }

    public void setAmountLeft(Double amountLeft) {
        this.amountLeft = amountLeft;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public PaymentPlan getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(PaymentPlan paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    public static class ComputeAccountData{
        CustomerAccount ca;
        Realm db;
        public ComputeAccountData(Transaction oldT, Account a, PaymentPlan p, Application app, Realm db){

            this.db = db;
            db.beginTransaction();
            CustomerAccount newCa = db.createObject(CustomerAccount.class);

             List<CustomerAccount> ca = db.where(CustomerAccount.class)
                    .equalTo("account.phone",a.getPhone())
                    .greaterThan("amountLeft",0.0)
                    .equalTo("paymentPlan.packageName",p.getPackageName())
                    .findAll();

             Transaction t = db.where(Transaction.class)
                     .equalTo("id",oldT.getId())
                     .findFirst();

            if(ca.isEmpty()){
               insertFirstTransaction(t,a,p, newCa);
            }else{
               insertNewTransaction(ca.get(ca.size()-1),t,a,p,newCa);
            }
        }

        private void insertFirstTransaction(Transaction t, Account a, PaymentPlan p, CustomerAccount ca){
            int id = 0;
            try{
                id = (int) (db.where(CustomerAccount.class).max("id").intValue() + 1);
            }catch(NullPointerException ex){
                Log.d("REALM_ERROR",ex.toString());
                id = 1;
            }

            Double amountRemaining = p.getAmount() - t.getAmount();

            ca.setId(id);
            ca.setTransaction(t);
            ca.setAccount(a);
            ca.setAmountLeft(amountRemaining);
            ca.setPaymentPlan(p);

            db.commitTransaction();
        }

        private void insertNewTransaction(CustomerAccount ca, Transaction t, Account a, PaymentPlan p, CustomerAccount newCa){
            int id = 0;
            try{
                id = (int) (db.where(CustomerAccount.class).max("id").intValue() + 1);
            }catch(NullPointerException ex){
                Log.d("REALM_ERROR",ex.toString());
                id = 1;
            }
            Double amountRemaining;
            Double amountRemainder;

            if(ca.getAmountLeft() > t.getAmount()){
                amountRemaining = ca.getAmountLeft() - t.getAmount();
                amountRemainder = 0.0;
            }else if(ca.getAmountLeft() == t.getAmount()) {
                amountRemaining = ca.getAmountLeft() - t.getAmount();
                amountRemainder = 0.0;
            }else{
                amountRemaining = 0.0;
                amountRemainder = t.getAmount() - ca.getAmountLeft();
            }

            newCa.setId(id);
            newCa.setTransaction(t);
            newCa.setAccount(a);
            newCa.setAmountLeft(amountRemaining);
            newCa.setPaymentPlan(p);



            db.commitTransaction();
        }
    }
}
