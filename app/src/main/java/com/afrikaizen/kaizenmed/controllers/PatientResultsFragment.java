package com.afrikaizen.kaizenmed.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afrikaizen.kaizenmed.R;
import com.afrikaizen.kaizenmed.adapters.DividerItemDecoration;
import com.afrikaizen.kaizenmed.adapters.PatientResultsListAdapter;

/**
 * Created by Steve on 07/08/2015.
 */
public class PatientResultsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerLayoutManager;
    RecyclerView.ItemDecoration recyclerItemDecoration;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_patient_results,container,false);
        recyclerView = (RecyclerView)v.findViewById(R.id.patient_results_list);
        recyclerView.setHasFixedSize(true);
        recyclerLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(recyclerLayoutManager);
        recyclerItemDecoration  = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(recyclerItemDecoration);

        String[] data = {"Joel Chingwizi", "Martha Mamombe", "Jack Banda","Alois Mamvura","Jane Ndove"};
        String[] data2 = {"Liver scerosis", "Diabetes Militus", "Heart condition diagnosis","Pending evaluation of cancer cells","brain tumor assesment"};

        recyclerAdapter = new PatientResultsListAdapter(data,data2);
        recyclerView.setAdapter(recyclerAdapter);
        return v;
    }
}