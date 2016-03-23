package com.afrikaizen.capstone.controllers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afrikaizen.capstone.R;

public class PaymentPlansFragment extends Fragment implements FloatingActionButton.OnClickListener{
    FloatingActionButton fb;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_payment_plans, container, false);
        fb = (FloatingActionButton)rootView.findViewById(R.id.fab);
        fb.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(),"Floating Action Button Clicked",Toast.LENGTH_LONG);
    }
}
