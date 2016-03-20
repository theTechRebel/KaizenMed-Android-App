package com.afrikaizen.kaizenmed.controllers;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afrikaizen.kaizenmed.R;

/**
 * Created by Steve on 21/3/2016.
 */
public class IncomingFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_incoming, container, false);
        return rootView;
    }
}
