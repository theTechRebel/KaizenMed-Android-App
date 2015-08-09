package com.afrikaizen.kaizenmed.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afrikaizen.kaizenmed.R;
import com.afrikaizen.kaizenmed.adapters.DividerItemDecoration;
import com.afrikaizen.kaizenmed.adapters.PatientResultsListAdapter;
import com.afrikaizen.kaizenmed.models.PatientsResults;

import java.util.ArrayList;

/**
 * Created by Steve on 07/08/2015.
 */
public class PatientResultsFragment extends Fragment {

    private RecyclerView recyclerView;
    private PatientResultsListAdapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerLayoutManager;
    RecyclerView.ItemDecoration recyclerItemDecoration;
    private SwipeRefreshLayout swipeToRefresh;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_patient_results,container,false);
        recyclerView = (RecyclerView)v.findViewById(R.id.patient_results_list);
        recyclerView.setHasFixedSize(true);
        recyclerLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(recyclerLayoutManager);
        recyclerItemDecoration  = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(recyclerItemDecoration);
        recyclerAdapter = new PatientResultsListAdapter(getActivity(),getPatientResults());
        recyclerView.setAdapter(recyclerAdapter);

        swipeToRefresh = (SwipeRefreshLayout)v.findViewById(R.id.swipeContainer);
        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerAdapter.clear();
                recyclerAdapter.addAll(getPatientResults());
                swipeToRefresh.setRefreshing(false);
            }
        });

        // Configure the refreshing colors
        swipeToRefresh.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return v;
    }

    private ArrayList<PatientsResults.JSONObject> getPatientResults() {
        ArrayList<PatientsResults.JSONObject> data = new ArrayList<>();
        data.add(new PatientsResults.JSONObject("Joel","Chingwizi","Lover Sclerosis","06-07-15","Positive test for lover tissue."));
        data.add(new PatientsResults.JSONObject("Alois","Mamvura","Lumber puncture","12-08-15","Lumber liquid shortage."));
        return data;
    }
}