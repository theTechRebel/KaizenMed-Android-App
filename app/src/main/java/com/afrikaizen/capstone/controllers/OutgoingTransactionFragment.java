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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.activities.OutgoingTransactionsListActivity;
import com.afrikaizen.capstone.activities.TransactionActivity;
import com.afrikaizen.capstone.models.Transaction;
import com.afrikaizen.capstone.models.Wallet;
import com.afrikaizen.capstone.orm.RealmService;
import com.afrikaizen.capstone.singleton.AppPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Steve on 9/5/2016.
 */
public class OutgoingTransactionFragment extends Fragment implements View.OnClickListener {
    TextView type,amountecocash,amounttelecash;
    EditText reciever, description,amount;
    Button initiateTransaction;
    String transactionType;
    private RadioGroup walletGroup;
    private RadioButton radioWalletButton,radioWalletButton2;
    Realm db;
    RealmResults<Wallet> w;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_outgoing_transaction_detail,container,false);
        type = (TextView)v.findViewById(R.id.transactionType);
        amountecocash = (TextView)v.findViewById(R.id.amountecocash);
        amounttelecash = (TextView)v.findViewById(R.id.amounttelecash);
        reciever = (EditText)v.findViewById(R.id.reciever);
        description = (EditText)v.findViewById(R.id.description);
        initiateTransaction = (Button)v.findViewById(R.id.save);
        walletGroup=(RadioGroup)v.findViewById(R.id.walletGroup);
        radioWalletButton = (RadioButton)v.findViewById(R.id.radioButton);
        radioWalletButton2 = (RadioButton)v.findViewById(R.id.radioButton2);
        amount = (EditText)v.findViewById(R.id.amount);
        initiateTransaction.setOnClickListener(this);

        type.setText(transactionType);

        db = RealmService.getInstance(getActivity().getApplication()).getRealm();

        Wallet w1 = db.where(Wallet.class)
                .equalTo("walletName", AppPreferences.getInstance(getActivity()).getEcoCashWallet())
                .findFirst();

        Wallet w2 = db.where(Wallet.class)
                .equalTo("walletName",AppPreferences.getInstance(getActivity()).getTeleCashWallet())
                .findFirst();

        amountecocash.setText("$"+w1.getBalance());
        amounttelecash.setText("$"+w2.getBalance());

        return v;

    }




    public void setOutGoingPaymentType(String type){
        this.transactionType = type;
    }

    @Override
    public void onClick(View v) {
        int selectedId=walletGroup.getCheckedRadioButtonId();
        String walletName="";
        switch (selectedId){
            case R.id.radioButton:
                walletName = "ecocash";
                break;
            case R.id.radioButton2:
                walletName = "telecash";
                break;
        }

        db.beginTransaction();
        Wallet w = new Wallet();
        if(walletName.matches("ecocash")){
            w = db.where(Wallet.class)
                    .equalTo("walletName", AppPreferences.getInstance(getActivity()).getEcoCashWallet())
                    .findFirst();
        }else if(walletName.matches("telecash")){
            w = db.where(Wallet.class)
                    .equalTo("walletName",AppPreferences.getInstance(getActivity()).getTeleCashWallet())
                    .findFirst();
        }

        Double amount = w.getBalance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");


        Transaction t = new Transaction();
        t.setWallet(walletName);
        t.setPhoneNumber(reciever.getText().toString());
        t.setPaymentType(transactionType);
        t.setDetails(description.getText().toString());
        t.setAmount(Double.parseDouble(this.amount.getText().toString()));
        t.setConfirmaionCode("Q42627282920.C537388");

        try {
            Date date = new Date();
            t.setDate(sdf.parse(sdf.format(date)));
        } catch (ParseException e) {
            Log.d("DATE-ERROR",e.toString());
        }
        try {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Harare"));
            Date time = calendar.getTime();

            t.setTime(timeFormat.parse(timeFormat.format(time)));
        } catch (ParseException e) {
            Log.d("DATE-ERROR",e.toString());
        }

        Double deduct = (amount - Double.parseDouble(this.amount.getText().toString()));



        int id = 0;
        try{
            id = (int) (db.where(Transaction.class).max("id").intValue() + 1);
        }catch(NullPointerException ex){
            Log.d("REALM_ERROR",ex.toString());
            id = 1;
        }
        t.setId(id);

        w.setBalance(deduct);

        db.copyToRealmOrUpdate(w);
        db.copyToRealm(t);
        db.commitTransaction();

        Intent intent = new Intent(getActivity(), OutgoingTransactionsListActivity.class);
        intent.putExtra("item", "outgoing");
        startActivity(intent);
    }
}
