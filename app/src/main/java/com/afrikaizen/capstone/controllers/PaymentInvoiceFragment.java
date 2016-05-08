package com.afrikaizen.capstone.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.activities.InvoicesActivity;
import com.afrikaizen.capstone.activities.TransactionActivity;
import com.afrikaizen.capstone.models.Invoices;
import com.afrikaizen.capstone.models.NewActivity;
import com.afrikaizen.capstone.models.Transaction;
import com.afrikaizen.capstone.orm.RealmService;
import com.afrikaizen.capstone.singleton.AppBus;

import io.realm.Realm;

/**
 * Created by Steve on 7/5/2016.
 */
public class PaymentInvoiceFragment extends Fragment implements View.OnClickListener {
        EditText item,price,quantity,customername,phonenumber;
        Button save, clear;
        TextView invoice_number;
        Realm db;
    Transaction t;
    Invoices i;

    public void setTransaction(Transaction t){
        this.t = t;
    }

@Override
public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppBus.getInstance().register(this);
        }

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_payment_invoice, container, false);

        db = RealmService.getInstance(getActivity().getApplication()).getRealm();

         i = db.where(Invoices.class)
                .equalTo("transaction.id",t.getId())
                .findFirst();



        item = (EditText) rootView.findViewById(R.id.payment_invoice_item_purchased);
        price = (EditText) rootView.findViewById(R.id.payment_invoice_item_price);
        quantity = (EditText) rootView.findViewById(R.id.payment_invoice_item_quantity);
        customername = (EditText) rootView.findViewById(R.id.payment_invoice_customer_name);
        phonenumber = (EditText) rootView.findViewById(R.id.payment_invoice_phone_number);
        invoice_number = (TextView) rootView.findViewById(R.id.payment_invoice_item_invoice_number);
        save = (Button)rootView.findViewById(R.id.payment_invoice_item_save);
        clear = (Button)rootView.findViewById(R.id.payment_invoice_item_clear);

        if(i!=null){
            item.setText(i.getItem());
            price.setText(i.getPrice().toString());
            quantity.setText(i.getQuantity().toString());
            customername.setText(i.getCustomername());
            phonenumber.setText(i.getPhonenumber());
        }

        String num = invoice_number.getText().toString();
        num = num + t.getId();
        invoice_number.setText(num);
        phonenumber.setText(t.getPhoneNumber());

        save.setOnClickListener(this);
        clear.setOnClickListener(this);
        return rootView;
        }

@Override
public void onClick(View v) {
        switch(v.getId()){
        case R.id.payment_invoice_item_clear:
            item.setText("");
            price.setText("");
            quantity.setText("");
            customername.setText("");

        break;

        case R.id.payment_invoice_item_save:
            Invoices invoice = new Invoices();

            int id;

            if(i!=null){
               id=i.getId();
            }else{
                try{
                    id = (int) (db.where(Transaction.class).max("id").intValue() + 1);
                }catch(NullPointerException ex){
                    Log.d("REALM_ERROR",ex.toString());
                    id = 1;
                }
            }


            invoice.setId(id);
            invoice.setInvoiceNumber(invoice_number.getText().toString());
            invoice.setItem(item.getText().toString());
            invoice.setPrice(Double.parseDouble(price.getText().toString()));
            invoice.setQuantity(Double.parseDouble(quantity.getText().toString()));
            invoice.setWallet(t.getWallet());
            invoice.setCustomername(customername.getText().toString());
            invoice.setPhonenumber(phonenumber.getText().toString());
            invoice.setDate(t.getDate());
            invoice.setTime(t.getTime());
            invoice.setTransaction(t);

            db.beginTransaction();
            db.copyToRealmOrUpdate(invoice);
            db.commitTransaction();

            Intent i = new Intent(getActivity().getApplicationContext(), InvoicesActivity.class);
            startActivity(i);
        break;
        }
        }
        }
