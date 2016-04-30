package com.afrikaizen.capstone.controllers;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.models.Account;
import com.afrikaizen.capstone.models.Transaction;
import com.afrikaizen.capstone.orm.RealmService;

import io.realm.Realm;

public class AccountEditFragment extends Fragment implements View.OnClickListener {
    private Account a;
    EditText firstName,surname,nationalIDnumber,emailAddress,accountNumber,
            phoneNumber,additoinalInformation;
    Button createAccount, backToPickContact;
    Realm db;

    public void setAccount(Account a) {
        this.a = a;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account_edit, container, false);

        db = RealmService.getInstance(getActivity().getApplication()).getRealm();

        firstName = (EditText)v.findViewById(R.id.acc_first_name);
        surname = (EditText)v.findViewById(R.id.acc_surname);
        nationalIDnumber = (EditText)v.findViewById(R.id.acc_idnumber);
        emailAddress = (EditText)v.findViewById(R.id.acc_email_address);
        accountNumber = (EditText)v.findViewById(R.id.acc_account_number);
        phoneNumber = (EditText)v.findViewById(R.id.acc_phone_number);
        additoinalInformation = (EditText)v.findViewById(R.id.acc_additional_info);
        createAccount = (Button)v.findViewById(R.id.acc_save);
        backToPickContact = (Button)v.findViewById(R.id.acc_back);

        firstName.setText(a.getName());
        surname.setText(a.getSurname());
        nationalIDnumber.setText(a.getIdNumber());
        emailAddress.setText(a.getEmail());
        accountNumber.setText(a.getAccountNumber());
        phoneNumber.setText(a.getPhone());
        additoinalInformation.setText(a.getAdditionalInformation());

        createAccount.setOnClickListener(this);
        backToPickContact.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.acc_save:
                if(checkParameters()){
                    a = setParameters();
                    int id = 0;
                    try{
                        id = (int) (db.where(Account.class).max("id").intValue() + 1);
                    }catch(NullPointerException ex){
                        Log.d("REALM_ERROR",ex.toString());
                        id = 1;
                    }
                    a.setId(id);
                    db.beginTransaction();
                    db.copyToRealmOrUpdate(a);
                    db.commitTransaction();

                    AccountFragment f = new AccountFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame, f);
                    fragmentTransaction.commit();
                }
                break;

            case R.id.acc_back:
                AccountFragment f = new AccountFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, f);
                fragmentTransaction.commit();
                break;
        }
    }

    private Boolean checkParameters(){
        Account acc = db.where(Account.class)
                .equalTo("idNumber", nationalIDnumber.getText().toString())
                .findFirst();
        if(acc != null){
            Log.d("ACCOUNT","ID Number already exists");
            return false;
        }
        acc = db.where(Account.class)
                .equalTo("phone",phoneNumber.getText().toString())
                .findFirst();
        if(acc != null){
            Log.d("ACCOUNT","Phone number already exists");
            return false;
        }
        acc = db.where(Account.class)
                .equalTo("email",accountNumber.getText().toString())
                .findFirst();
        if(acc != null){
            Log.d("ACCOUNT","Email address already exists");
            return false;
        }
        acc = db.where(Account.class)
                .equalTo("accountNumber",emailAddress.getText().toString())
                .findFirst();
        if(acc != null){
            Log.d("ACCOUNT","Email address already exists");
            return false;
        }
        return true;
    }

    private Account setParameters(){
        this.a.setName(firstName.getText().toString());
        this.a.setSurname(surname.getText().toString());
        this.a.setAccountNumber(accountNumber.getText().toString());
        this.a.setAdditionalInformation(additoinalInformation.getText().toString());
        this.a.setEmail(emailAddress.getText().toString());
        this.a.setIdNumber(nationalIDnumber.getText().toString());
        this.a.setPhone(phoneNumber.getText().toString());
        return this.a;
    }
}
