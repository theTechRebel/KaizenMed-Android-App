package com.afrikaizen.kaizenmed.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afrikaizen.kaizenmed.R;
import com.afrikaizen.kaizenmed.adapters.DividerItemDecoration;
import com.afrikaizen.kaizenmed.adapters.PatientResultsListAdapter;
import com.afrikaizen.kaizenmed.models.PatientsResults;
import com.afrikaizen.kaizenmed.singleton.AppBus;
import com.afrikaizen.kaizenmed.singleton.AppPreferences;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 07/08/2015.
 */
public class PatientResultsFragment extends Fragment
        implements PatientResultsListAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private PatientResultsListAdapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerLayoutManager;
    RecyclerView.ItemDecoration recyclerItemDecoration;
    private SwipeRefreshLayout swipeToRefresh;
    ArrayList<PatientsResults.JSONObject> results;
    private String BUNDLE_KEY = "ArrayList";


    @Override
    public void onResume() {
        super.onResume();
        AppBus.getInstance().register(this);
        if(results.size() == 0){
            getPatientResults();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        AppBus.getInstance().unregister(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            results = new ArrayList<PatientsResults.JSONObject>();
        }else{
            results = (ArrayList<PatientsResults.JSONObject>)savedInstanceState.getSerializable(BUNDLE_KEY);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BUNDLE_KEY, results);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_patient_results,container,false);
        recyclerView = (RecyclerView)v.findViewById(R.id.patient_results_list);
        recyclerView.setHasFixedSize(true);
        recyclerLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(recyclerLayoutManager);
        recyclerItemDecoration  = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(recyclerItemDecoration);
        recyclerAdapter = new PatientResultsListAdapter(getActivity(),results);
        recyclerAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(recyclerAdapter);
        swipeToRefresh = (SwipeRefreshLayout)v.findViewById(R.id.swipeContainer);
        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPatientResults();
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

    private void getPatientResults() {
        swipeToRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeToRefresh.setRefreshing(true);
            }
        });
        AppBus.getInstance().
                post(new PatientsResults.Data(
                        AppPreferences.getInstance(getActivity()).getDoctorsID(),
                        AppPreferences.getInstance(getActivity()).getDoctorsName()
                ));
    }

    @Subscribe
    public void displayPatientResults(ArrayList<PatientsResults.JSONObject> results){
        this.results.clear();
        this.results.addAll(results);
        recyclerAdapter.clear();
        recyclerAdapter.addAll(results);
        swipeToRefresh.setRefreshing(false);
    }

    @Subscribe
    public void displayError(PatientsResults.Error error){
        swipeToRefresh.setRefreshing(false);
        Toast.makeText(getActivity(),error.getError(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(View itemView, int position) {
        PatientsResults.JSONObject result = this.results.get(position);
        Fragment f = new PatientResultFragment();
        Bundle args = new Bundle();
        args.putSerializable(PatientResultFragment.RESULT, result);
        f.setArguments(args);
        AppBus.getInstance().post(f);
    }
}