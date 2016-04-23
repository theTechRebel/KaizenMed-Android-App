package com.afrikaizen.capstone.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.activities.PaymentHistoryActivity;
import com.afrikaizen.capstone.activities.TransactionActivity;
import com.afrikaizen.capstone.adapters.TransactonListAdapter;
import com.afrikaizen.capstone.models.Transaction;
import com.afrikaizen.capstone.orm.RealmService;
import com.afrikaizen.capstone.singleton.AppBus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Steve on 19/3/2016.
 */
public class TransactionFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private  String wallet;
    private RecyclerView recyclerView;
    private TransactonListAdapter adapter;
    private RecyclerView.ItemDecoration recyclerItemDecoration;
    private SwipeRefreshLayout swipeToRefresh;
    ArrayList<Transaction> data =
            new ArrayList<Transaction>(Arrays.<Transaction>asList());
    Transaction t;
    RealmResults<Transaction> result;
    RealmQuery<Transaction> query;
    Realm db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transactions, container, false);

        Bundle b = getArguments();
        wallet = b.getString("WALLET");
        Log.d("WALLET",wallet);

        db = RealmService.getInstance(getActivity().getApplication()).getRealm();

        data = new ArrayList<Transaction>();

        recyclerView = (RecyclerView)rootView.findViewById(R.id.transaction_list);
        adapter = new TransactonListAdapter(getData("*"), this);
        data.addAll(getData("*"));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1500);
        itemAnimator.setRemoveDuration(1500);
        recyclerView.setItemAnimator(itemAnimator);
        //recyclerItemDecoration  = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        //recyclerView.addItemDecoration(recyclerItemDecoration);
        swipeToRefresh = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeContainer);
        swipeToRefresh.setOnRefreshListener(this);
        swipeToRefresh.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return rootView;
    }



    private List<Transaction> getData(String params){
        List<Transaction> data = new ArrayList();
        if(params == "*"){
            query = db.where(Transaction.class)
                    .equalTo("wallet",this.wallet);
            result = query.findAll();
            data.addAll(result);
        }else{
            query = db.where(Transaction.class);
            result = db.where(Transaction.class)
                    .equalTo("packageName", params)
                    .findAll();
            data.addAll(result);
        }
    return data;
    }

    @Override
    public void onRefresh() {

        swipeToRefresh.setRefreshing(true);
        swipeToRefresh.post(new Runnable() {
            @Override
            public void run() {
                Handler delay = new Handler();
                delay.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //adapter.setData(getData());
                        data.clear();
                        data.addAll(getData("*"));
                        Transaction t = new Transaction();
                        t.setPaymentType("all");
                        displayDataChange(t);
                        swipeToRefresh.setRefreshing(false);
                    }
                },7000);
            }
        });
    }

    @Subscribe
    public void onReceiveTransaction(ArrayList<Transaction> t){
        Transaction trans = t.get(0);
        if(trans.getWallet().matches(this.wallet)){
            data.addAll(t);
            adapter.addItem(t.get(0));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AppBus.getInstance().register(this);
    }


      @Override
      public void onPause() {
       super.onPause();
        AppBus.getInstance().unregister(this);
      }

    @Subscribe
    public void displayDataChange(Transaction t){
        this.t = t;
        if (t.getPaymentType().matches("all")) {
            adapter.changeData(data);
        }else {
            List<Transaction>filteredData = new ArrayList<Transaction>();
            for (int i = 0; i<data.size(); i++)
                if(data.get(i).getPaymentType().matches(t.getPaymentType()))
                    filteredData.add(data.get(i));

            adapter.changeData(filteredData);
        }
        adapter.notifyDataSetChanged();
    }

    public void displayDataChange(){
        displayDataChange(this.t);
    }

    public RecyclerView getRecyclerView(){
        return this.recyclerView;
    }

    public void handleClick(Transaction t){
        Bundle b = new Bundle();
        b.putInt("TRANSACTION_ID",t.getId());
        Intent intent = new Intent(getActivity().getApplicationContext(), PaymentHistoryActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }
}
