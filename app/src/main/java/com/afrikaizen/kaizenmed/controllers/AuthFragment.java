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
import com.afrikaizen.kaizenmed.singleton.AppBus;
import com.afrikaizen.kaizenmed.singleton.AppPreferences;
import com.squareup.otto.Subscribe;

/**
 * Created by Steve on 07/08/2015.
 */
public class AuthFragment extends Fragment implements View.OnClickListener{
    EditText doctorsName, password;
    Button btnLogin;
    Doctor.Data doc;
    private ProgressBar spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_auth, container, false);
        doctorsName = (EditText)rootView.findViewById(R.id.doctorsName);
        password = (EditText)rootView.findViewById(R.id.password);
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
        if(doctorsName.getText().toString().matches("")){
            Toast.makeText(getActivity(), "Please fill in the doctors name",
                    Toast.LENGTH_LONG).show();
        }else if (password.getText().toString().matches("")){
            Toast.makeText(getActivity(), "Please fill in the password",
                    Toast.LENGTH_LONG).show();
        }else{
            doc = new Doctor.Data(doctorsName.getText().toString(),password.getText().toString());
            AppBus.getInstance().post(doc);
            spinner.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe
    public void successAfterLoginClick(Doctor.JSONObject doc){
        spinner.setVisibility(View.GONE);
        AppPreferences.getInstance(getActivity())
                .setDoctorsName(doc.getName() + " " + doc.getSurname());
        AppPreferences.getInstance(getActivity()).setDoctorsID(doc.getId());
        AppBus.getInstance().post(new NewActivity(0));
    }

    @Subscribe
    public void failureAfterLoginClick(Doctor.Error e){
        spinner.setVisibility(View.GONE);
        Toast.makeText(getActivity(), e.getError(), Toast.LENGTH_LONG).show();
    }
}

