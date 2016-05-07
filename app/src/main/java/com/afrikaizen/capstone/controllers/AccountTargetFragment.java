package com.afrikaizen.capstone.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.activities.PaymentHistoryActivity;
import com.afrikaizen.capstone.adapters.AccountListAdapter;
import com.afrikaizen.capstone.adapters.AccountTargetListAdapter;
import com.afrikaizen.capstone.models.Account;
import com.afrikaizen.capstone.models.Target;
import com.afrikaizen.capstone.orm.RealmService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Steve on 15/4/2016.
 */
public class AccountTargetFragment extends Fragment {
    private Target target;

    public Target getTarget() {
        return target;
    }

    ArrayList<Target> data =
            new ArrayList<Target>(Arrays.<Target>asList());
    private RecyclerView recyclerView;
    private AccountTargetListAdapter adapter;
    private RecyclerView.ItemDecoration recyclerItemDecoration;
    RealmList<Target> result;
    RealmQuery<Target> query;
    Account a;
    Realm db;

    public void setAccount(Account a){
        this.a = a;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_target, container, false);

        db = RealmService.getInstance(getActivity().getApplication()).getRealm();

        adapter = new AccountTargetListAdapter(a.getTargets(),this.a,this);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.transaction_list);
        data.addAll(a.getTargets());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        return rootView;
    }

    public void handleClick(Target t, Account a){
        Bundle b = new Bundle();
        b.putInt("TARGET_ID",t.getId());
        b.putString("TARGET_CUSTOMER", a.getAccountNumber());
        Intent intent = new Intent(getActivity().getApplicationContext(), PaymentHistoryActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }


}
