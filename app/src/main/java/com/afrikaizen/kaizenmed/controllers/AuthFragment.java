package com.afrikaizen.kaizenmed.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afrikaizen.kaizenmed.R;
import com.afrikaizen.kaizenmed.models.Doctor;
import com.afrikaizen.kaizenmed.models.NewActivity;
import com.afrikaizen.kaizenmed.models.Wallet;
import com.afrikaizen.kaizenmed.orm.RealmService;
import com.afrikaizen.kaizenmed.singleton.AppBus;
import com.afrikaizen.kaizenmed.singleton.AppPreferences;
import com.squareup.otto.Subscribe;

import io.realm.Realm;

/**
 * Created by Steve on 07/08/2015.
 */
public class AuthFragment extends Fragment implements View.OnClickListener{
    EditText organisationName, ecocashBillerCode, telecashBillerCode;
    Button btnLogin;
    Wallet wallet;
    private ProgressBar spinner;
    Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        realm = RealmService.getInstance(this.getActivity().getApplication()).getRealm();

        View rootView = inflater.inflate(R.layout.fragment_auth, container, false);
        organisationName = (EditText)rootView.findViewById(R.id.organisationName);
        ecocashBillerCode = (EditText)rootView.findViewById(R.id.ecocashBillerCode);
        telecashBillerCode = (EditText)rootView.findViewById(R.id.telecashBillerCode);
        btnLogin = (Button)rootView.findViewById(R.id.btnLogin);
        spinner = (ProgressBar) rootView.findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        btnLogin.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        AppBus.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        AppBus.getInstance().unregister(this);
    }

    @Override
    public void onClick(View v) {
        if(organisationName.getText().toString().matches("")){
            Toast.makeText(getActivity(), "Please fill in the organisations name.",
                    Toast.LENGTH_LONG).show();
        }else if (ecocashBillerCode.getText().toString().matches("") ||
                telecashBillerCode.getText().toString().matches("")){
            Toast.makeText(getActivity(), "Please fill in Telecash or Ecocash wallet details..",
                    Toast.LENGTH_LONG).show();
        }else{
            wallet = new Wallet(organisationName.getText().toString(),
                    ecocashBillerCode.getText().toString(),
                    telecashBillerCode.getText().toString());

            realm.beginTransaction();
            realm.copyToRealm(wallet);
            realm.commitTransaction();

            AppBus.getInstance().post(wallet);
            spinner.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe
    public void successAfterLoginClick(Wallet wallet){
        spinner.setVisibility(View.GONE);
        AppPreferences.getInstance(getActivity())
                .setOrganisationName(wallet.getOrganisationName());
        AppPreferences.getInstance(getActivity()).setEcoCashWallet(wallet.getEcocashWallet());
        AppPreferences.getInstance(getActivity()).setTeleCashWallet(wallet.getTelecashWallte());
        AppPreferences.getInstance(getActivity()).setDataPersisted(true);
        AppPreferences.getInstance(getActivity()).setDataPersisted(true);
        AppBus.getInstance().post(new NewActivity(0));
    }

    @Subscribe
    public void failureAfterLoginClick(Doctor.Error e){
        spinner.setVisibility(View.GONE);
        Toast.makeText(getActivity(), e.getError(), Toast.LENGTH_LONG).show();
    }
}

