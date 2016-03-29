package com.afrikaizen.capstone.controllers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.adapters.PaymentPlanListAdapter;
import com.afrikaizen.capstone.imports.DividerItemDecoration;
import com.afrikaizen.capstone.models.PaymentPlan;
import com.afrikaizen.capstone.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class PaymentPlansFragment extends Fragment implements FloatingActionButton.OnClickListener{
    FloatingActionButton fb;
    private RecyclerView recyclerView;
    private PaymentPlanListAdapter adapter;
    private RecyclerView.ItemDecoration recyclerItemDecoration;
    private SwipeRefreshLayout swipeToRefresh;
    List<PaymentPlan> data;
    PaymentPlan p;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_payment_plans, container, false);

        data = new ArrayList<PaymentPlan>();

        recyclerView = (RecyclerView)rootView.findViewById(R.id.payment_plans_list);
        adapter = new PaymentPlanListAdapter(getData());
        data.addAll(getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerItemDecoration  = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(recyclerItemDecoration);

        fb = (FloatingActionButton)rootView.findViewById(R.id.fab);
        fb.setOnClickListener(this);
        return rootView;
    }

    private static List<PaymentPlan> getData(){
        List<PaymentPlan> data = new ArrayList();

        int[] period= {60,30,15};
        String[] description= {"Largest Package","Second Largest Package","Third Largest Package"};
        Double[] amount= {1000.00,700.00,500.00};
        String[] packageName= {"Gold Package","Silver Package","Bronze Package"};


        for (int i=0; i<period.length;i++){
            PaymentPlan current = new PaymentPlan();
            current.setAmount(amount[i]);
            current.setDescription(description[i]);
            current.setPackageName(packageName[i]);
            current.setPeriod(period[i]);
            data.add(current);
        }

        return data;
    }



    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(),"Floating Action Button Clicked",Toast.LENGTH_LONG);
    }
}
