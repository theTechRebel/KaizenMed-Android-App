package com.afrikaizen.capstone.controllers;

import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.models.NewActivity;
import com.afrikaizen.capstone.models.Wallet;
import com.afrikaizen.capstone.orm.RealmService;
import com.afrikaizen.capstone.singleton.AppBus;
import com.afrikaizen.capstone.singleton.AppPreferences;

import io.realm.Realm;

/**
 * Created by Steve on 27/4/2016.
 */
public class ConfigureWalletBalance extends Fragment implements View.OnClickListener {
    EditText ecocash, telecash;
    Button save, clear;
    Realm db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        db = RealmService.getInstance(getActivity().getApplication()).getRealm();

        View v = inflater.inflate(R.layout.fragment_configure_wallet,container,false);
        ecocash = (EditText)v.findViewById(R.id.config_ecocash);
        telecash = (EditText)v.findViewById(R.id.config_telecash);
        save = (Button)v.findViewById(R.id.config_save);
        clear = (Button)v.findViewById(R.id.config_clear);

        save.setOnClickListener(this);
        clear.setOnClickListener(this);

        if(AppPreferences.getInstance(getActivity()).getTeleCashWallet()
                != AppPreferences.getInstance(getActivity()).DEFAULT_VALUE_STRING){
            ecocash.setActivated(true);
        }else{
            ecocash.setActivated(false);
        }

        if(AppPreferences.getInstance(getActivity()).getEcoCashWallet()
                != AppPreferences.getInstance(getActivity()).DEFAULT_VALUE_STRING){
            telecash.setActivated(true);
        }else{
            telecash.setActivated(false);
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.config_save:
                db.beginTransaction();

                if(AppPreferences.getInstance(getActivity()).getTeleCashWallet()
                        != AppPreferences.getInstance(getActivity()).DEFAULT_VALUE_STRING){
                    Wallet ecocash = db.where(Wallet.class)
                            .equalTo("walletName",AppPreferences.getInstance(getActivity()).getEcoCashWallet())
                            .findFirst();
                    ecocash.setBalance(Double.parseDouble(this.ecocash.getText().toString()));
                }

                if(AppPreferences.getInstance(getActivity()).getEcoCashWallet()
                        != AppPreferences.getInstance(getActivity()).DEFAULT_VALUE_STRING){
                    Wallet telecash = db.where(Wallet.class)
                            .equalTo("walletName",AppPreferences.getInstance(getActivity()).getTeleCashWallet())
                            .findFirst();
                    telecash.setBalance(Double.parseDouble(this.telecash.getText().toString()));
                }
                db.commitTransaction();
                AppBus.getInstance().post(new NewActivity(0));
                break;
            case R.id.config_clear:
                this.ecocash.setText("");
                this.telecash.setText("");
                break;
        }
    }
}
