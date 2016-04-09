package com.afrikaizen.capstone.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.adapters.PaymentPlanListAdapter;
import com.afrikaizen.capstone.imports.DividerItemDecoration;
import com.afrikaizen.capstone.models.NewActivity;
import com.afrikaizen.capstone.models.PaymentPlan;
import com.afrikaizen.capstone.singleton.AppBus;

import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;
import net.i2p.android.ext.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

public class PaymentPlansFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private PaymentPlanListAdapter adapter;
    private RecyclerView.ItemDecoration recyclerItemDecoration;
    List<PaymentPlan> data;
    PaymentPlan p;
    FloatingActionsMenu menuMultipleActions;
    FloatingActionButton addPaymentPlan;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppBus.getInstance().register(this);
    }

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
        //recyclerItemDecoration  = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        //recyclerView.addItemDecoration(recyclerItemDecoration);

        addPaymentPlan = (FloatingActionButton) rootView.findViewById(R.id.add_plan);
        addPaymentPlan.setOnClickListener(this);
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
        AppBus.getInstance().post(new NewActivity(1));
    }
}
