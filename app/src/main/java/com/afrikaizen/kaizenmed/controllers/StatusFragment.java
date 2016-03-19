package com.afrikaizen.kaizenmed.controllers;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afrikaizen.kaizenmed.R;

/**
 * Created by Steve on 19/3/2016.
 */
public class StatusFragment extends Fragment {
    TextView sim;
    Context ctx;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_status, container, false);
        sim = (TextView)rootView.findViewById(R.id.SimDetails);
        ctx = getActivity().getApplicationContext();
        TelephonyManager telemamanger = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        String getSimSerialNumber = telemamanger.getSimSerialNumber();
        String getSimNumber = telemamanger.getLine1Number();
        sim.setText(getSimNumber +" "+getSimNumber);
        return rootView;
    }
}
