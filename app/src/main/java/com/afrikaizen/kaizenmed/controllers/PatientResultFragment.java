package com.afrikaizen.kaizenmed.controllers;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afrikaizen.kaizenmed.R;
import com.afrikaizen.kaizenmed.models.PatientsResults;

public class PatientResultFragment extends Fragment {

    PatientsResults.JSONObject result;
    TextView txtPatientName, txtCondition, txtDate, txtResult;
    public static String RESULT = "result";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_patient_result, container, false);
        txtPatientName = (TextView)rootView.findViewById(R.id.txtPatientNameAndSurname);
        txtCondition = (TextView)rootView.findViewById(R.id.txtCondition);
        txtDate = (TextView)rootView.findViewById(R.id.txtTreated);
        txtResult = (TextView)rootView.findViewById(R.id.txtResults);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        result = (PatientsResults.JSONObject)getArguments().getSerializable(RESULT);

        txtPatientName.setText(result.getName()+" "+result.getSurname());
        txtCondition.setText(result.getCondition());
        txtDate.setText(result.getTreated());
        txtResult.setText(result.getResults());
    }
}
