package com.afrikaizen.capstone.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.models.Doctor;
import com.afrikaizen.capstone.models.NewActivity;
import com.afrikaizen.capstone.models.Wallet;
import com.afrikaizen.capstone.orm.RealmService;
import com.afrikaizen.capstone.singleton.AppBus;
import com.afrikaizen.capstone.singleton.AppPreferences;
import com.squareup.otto.Subscribe;

import io.realm.Realm;

/**
 * Created by Steve on 07/08/2015.
 */
public class AuthFragment extends Fragment implements View.OnClickListener{
    EditText organisationName, ecocashBillerCode, telecashBillerCode;
    Spinner spinner1, spinner2;
    Button btnLogin;
    private ProgressBar spinner;
    Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        realm = RealmService.getInstance(this.getActivity().getApplication()).getRealm();

        View rootView = inflater.inflate(R.layout.fragment_auth, container, false);
        spinner1 = (Spinner)rootView.findViewById(R.id.sim_spinner1);
        spinner2 = (Spinner) rootView.findViewById(R.id.sim_spinner2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.sim, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);



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
                telecashBillerCode.getText().toString().matches("")) {
            Toast.makeText(getActivity(), "Please fill in Telecash or Ecocash wallet details.",
                    Toast.LENGTH_LONG).show();
        }else if(spinner1.getSelectedItem().toString().matches("") || spinner2.getSelectedItem().toString().matches("")){
            Toast.makeText(getActivity(), "please select the SIM for each Wallet Account.",
                    Toast.LENGTH_LONG).show();
        }else{
            Wallet wallet1 = new Wallet(organisationName.getText().toString(),
                    ecocashBillerCode.getText().toString(),
                    spinner1.getSelectedItem().toString());

            Wallet wallet2 = new Wallet(organisationName.getText().toString(),
                    telecashBillerCode.getText().toString(),
                    spinner2.getSelectedItem().toString());

            realm.beginTransaction();
            realm.copyToRealm(wallet1);
            realm.copyToRealm(wallet2);
            realm.commitTransaction();

            spinner.setVisibility(View.VISIBLE);
            successAfterLoginClick();
        }
    }

    public void successAfterLoginClick(){
        spinner.setVisibility(View.GONE);
        AppPreferences.getInstance(getActivity())
                .setOrganisationName(organisationName.getText().toString());
        AppPreferences.getInstance(getActivity()).setEcoCashWallet(ecocashBillerCode.getText().toString());
        AppPreferences.getInstance(getActivity()).setTeleCashWallet(telecashBillerCode.getText().toString());
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

