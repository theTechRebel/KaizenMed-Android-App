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
import com.afrikaizen.capstone.models.Account;
import com.afrikaizen.capstone.models.NewActivity;
import com.afrikaizen.capstone.models.PaymentPlan;
import com.afrikaizen.capstone.orm.RealmService;
import com.afrikaizen.capstone.singleton.AppBus;
import com.squareup.otto.Subscribe;

import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;
import net.i2p.android.ext.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class PaymentPlansFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private PaymentPlanListAdapter adapter;
    List<PaymentPlan> data;
    RealmResults<PaymentPlan> result;
    FloatingActionButton addPaymentPlan;
    RealmQuery<PaymentPlan> query;
    Realm db;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppBus.getInstance().register(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_payment_plans, container, false);
        db = RealmService.getInstance(getActivity().getApplication()).getRealm();

        data = new ArrayList<PaymentPlan>();

        recyclerView = (RecyclerView)rootView.findViewById(R.id.payment_plans_list);
        adapter = new PaymentPlanListAdapter(getData("*"));
        data.addAll(getData("*"));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        //recyclerItemDecoration  = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        //recyclerView.addItemDecoration(recyclerItemDecoration);

        addPaymentPlan = (FloatingActionButton) rootView.findViewById(R.id.add_plan);
        addPaymentPlan.setOnClickListener(this);
        return rootView;
    }

    private List<PaymentPlan> getData(String params){
        if(params == "*"){
            query = db.where(PaymentPlan.class);
            result = query.findAll();
        }else{
            query = db.where(PaymentPlan.class);
            result = db.where(PaymentPlan.class)
                    .equalTo("packageName", params)
                    .findAll();
        }

        return result;
    }

    @Override
    public void onClick(View v) {
        AppBus.getInstance().post(new NewActivity(1));
    }
}
