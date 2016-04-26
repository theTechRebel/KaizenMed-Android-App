package com.afrikaizen.capstone.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.adapters.PaymentHistoryAdapter;
import com.afrikaizen.capstone.models.CustomerAccount;
import com.afrikaizen.capstone.models.Transaction;
import com.afrikaizen.capstone.orm.RealmService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Steve on 23/4/2016.
 */
public class PaymentHistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private PaymentHistoryAdapter adapter;
    ArrayList<CustomerAccount> data =
            new ArrayList<CustomerAccount>(Arrays.<CustomerAccount>asList());
    Transaction t = null;
    Realm db;

    TextView customerDetails,planDetails;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_payment_history,container,false);

        customerDetails = (TextView)rootView.findViewById(R.id.payment_history_customer_details);
        planDetails = (TextView)rootView.findViewById(R.id.payment_history_package_details);

        customerDetails.setText(t.getCustomerDetails());
        planDetails.setText(t.getDetails());

        db = RealmService.getInstance(getActivity().getApplication()).getRealm();
        data = new ArrayList<CustomerAccount>();
        recyclerView = (RecyclerView)rootView.findViewById(R.id.transaction_history_list);
        adapter = new PaymentHistoryAdapter(getData("*"));
        data.addAll(getData("*"));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        return rootView;
    }

    private List<CustomerAccount> getData(String param){
        RealmQuery<CustomerAccount> query = db.where(CustomerAccount.class)
                .equalTo("account.phone",t.getPhoneNumber())
                .equalTo("paymentPlan.packageName",t.getPaymentPlan().getPackageName());
        RealmResults<CustomerAccount> results = query.findAll();
        return results;
    }

    public RecyclerView getRecyclerView(){
        return this.recyclerView;
    }

    public void setTransaction(Transaction t){
        this.t = t;
    }
}
