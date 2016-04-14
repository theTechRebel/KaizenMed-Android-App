package com.afrikaizen.capstone.controllers;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.adapters.AccountListAdapter;
import com.afrikaizen.capstone.adapters.PaymentPlanListAdapter;
import com.afrikaizen.capstone.imports.DividerItemDecoration;
import com.afrikaizen.capstone.models.Account;
import com.afrikaizen.capstone.models.NewActivity;
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

/**
 * Created by Steve on 31/3/2016.
 */
public class AccountsFragment extends Fragment implements View.OnClickListener{
    private RecyclerView recyclerView;
    private AccountListAdapter adapter;
    private RecyclerView.ItemDecoration recyclerItemDecoration;
    RealmResults<Account> result;
    RealmQuery<Account> query;
    List<Account> data;
    Account a;
    Realm db;

    FloatingActionButton contacts;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppBus.getInstance().register(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_accounts, container, false);
        db = RealmService.getInstance(getActivity().getApplication()).getRealm();

        data = new ArrayList<Account>();
        adapter = new AccountListAdapter(getData("*"),this);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.payment_plans_list);
        data.addAll(getData("*"));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        //recyclerItemDecoration  = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        //recyclerView.addItemDecoration(recyclerItemDecoration);


        contacts = (FloatingActionButton) rootView.findViewById(R.id.contacts);
        contacts.setTitle("Add new customer account");
        contacts.setOnClickListener(this);
        return rootView;
    }

    private List<Account> getData(String params){
        if(params == "*"){
            query = db.where(Account.class);
            result = query.findAll();
        }else{
            query = db.where(Account.class);
            result = db.where(Account.class)
                    .equalTo("phone", params)
                    .findAll();
        }

        return result;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //TODO: Add search view to filter in app contacts "accounts" by search query
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.contacts:
                AppBus.getInstance().post(new NewActivity(0));
                break;
        }
    }

    public void onClick(Account a){
        AppBus.getInstance().post(a);
    }

    @Subscribe
    public void addAccount(Account a){
        db.beginTransaction();
        db.copyToRealmOrUpdate(a);
        db.commitTransaction();

        adapter.addItem(a);
        adapter.notifyDataSetChanged();
    }

    public RecyclerView getRecyclerView(){
        return this.recyclerView;
    }


}
