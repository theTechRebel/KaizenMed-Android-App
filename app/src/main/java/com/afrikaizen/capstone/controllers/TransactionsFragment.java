package com.afrikaizen.capstone.controllers;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.adapters.TransactonListAdapter;
import com.afrikaizen.capstone.imports.DividerItemDecoration;
import com.afrikaizen.capstone.models.Transaction;
import com.afrikaizen.capstone.singleton.AppBus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 19/3/2016.
 */
public class TransactionsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerView;
    private TransactonListAdapter adapter;
    private RecyclerView.ItemDecoration recyclerItemDecoration;
    private SwipeRefreshLayout swipeToRefresh;
    List<Transaction> data;
    Transaction t;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transactions, container, false);

        data = new ArrayList<Transaction>();

        recyclerView = (RecyclerView)rootView.findViewById(R.id.transaction_list);
        adapter = new TransactonListAdapter(getData());
        data.addAll(getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerItemDecoration  = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(recyclerItemDecoration);
        swipeToRefresh = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeContainer);
        swipeToRefresh.setOnRefreshListener(this);
        swipeToRefresh.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return rootView;
    }



    private static List<Transaction> getData(){
        List<Transaction> data = new ArrayList();

        String[] time = {"6:00","7:00","8:00","9:00","3:30","4:53","4:15","2:12"};
        String[] paymentType= {"Incoming Payment","Incoming Payment","Incoming Payment","Outgoing Payment","Outgoing Payment","Incoming Payment","Refund","Transaaction Reversal"};
        String[] date= {"05/03/16","05/03/16","05/03/16","05/03/16","07/04/16","12/09/16","25/03/16","01/010/16"};
        String[] customerDetails= {"Manuel Pelegrini","Bakari Sagna","Alexandra Kolarov","Joe Hart","Sergio-Kun Aguero","Wilfred Bony","Nicholas Otamendi","Vincent Kompany"};
        String[] amount= {"$30.00","$55.00","$70.00","$100.00","$22.50","$300.00","$160.00","$10.00"};
        String[] details= {"Gold Package","Silver Package","Bronze Package","Silver Package","Bronze Package","Siver Package","Gold Package","Bronze Package"};
        String[] confirmaionCode= {"234567C78","098765F879","02561H67122","32332329H8989","02561H67122","32332329H8989","02561H67122","32332329H8989"};

        for (int i=0; i<time.length;i++){
            Transaction current = new Transaction();
            current.setAmount(amount[i]);
            current.setConfirmaionCode(confirmaionCode[i]);
            current.setCustomerDetails(customerDetails[i]);
            current.setDate(date[i]);
            current.setDetails(details[i]);
            current.setPaymentType(paymentType[i]);
            current.setTime(time[i]);

            data.add(current);
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
                        data.addAll(getData());
                        //adapter.notifyDataSetChanged();
                        displayDataChange();
                        swipeToRefresh.setRefreshing(false);
                    }
                },7000);
            }
        });

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
        Log.d("item","changing data to: "+t.getPaymentType());
        this.t = t;
        if (t.getPaymentType() == "all") {
            adapter.changeData(data);
        }else {
            List<Transaction> allData  = new ArrayList<Transaction>();
            List<Transaction>filteredData = new ArrayList<Transaction>();

            allData.addAll(data);
            for (int i = 0; i<allData.size(); i++)
                if(allData.get(i).getPaymentType().matches(t.getPaymentType()))
                    filteredData.add(allData.get(i));

            adapter.changeData(filteredData);
        }

        adapter.notifyDataSetChanged();
    }

    public void displayDataChange(){
        displayDataChange(this.t);
    }

}
