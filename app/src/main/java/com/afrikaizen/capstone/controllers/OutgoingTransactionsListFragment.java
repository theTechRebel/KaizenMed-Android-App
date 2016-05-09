package com.afrikaizen.capstone.controllers;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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
import com.afrikaizen.capstone.adapters.OutgoingTransactionsListAdapter;
import com.afrikaizen.capstone.adapters.TransactonListAdapter;
import com.afrikaizen.capstone.models.Transaction;
import com.afrikaizen.capstone.orm.RealmService;
import com.afrikaizen.capstone.singleton.AppBus;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.squareup.otto.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Steve on 9/5/2016.
 */
public class OutgoingTransactionsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private  String wallet;
    private String transactionType;

    private RecyclerView recyclerView;
    private OutgoingTransactionsListAdapter adapter;
    private RecyclerView.ItemDecoration recyclerItemDecoration;
    private SwipeRefreshLayout swipeToRefresh;
    ArrayList<Transaction> data =
            new ArrayList<Transaction>(Arrays.<Transaction>asList());
    Transaction t;
    RealmResults<Transaction> result;
    RealmQuery<Transaction> query;
    Realm db;

    protected BarChart mChart;
    ArrayList<BarEntry> entries = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transactions, container, false);

        Bundle b = getArguments();
        wallet = b.getString("WALLET");
        Log.d("WALLET",wallet);

        transactionType = b.getString("TRANSACTION");

        db = RealmService.getInstance(getActivity().getApplication()).getRealm();
        mChart = (BarChart) rootView.findViewById(R.id.chart1);

        try {
            setUpWeeklyBarChart();
        } catch (ParseException e) {
            Log.d("BAR_CHART_ERROR",e.toString());
        }

        data = new ArrayList<Transaction>();

        recyclerView = (RecyclerView)rootView.findViewById(R.id.transaction_list);
        adapter = new OutgoingTransactionsListAdapter(getData("*"), this);
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
                        .notEqualTo("paymentType","Incoming Payment")
                        .equalTo("wallet",this.wallet);
                result = query.findAll();
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
                },3000);
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
    }


    private static Date[] getDaysOfWeek(Date refDate, int firstDayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(refDate);
        calendar.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
        Date[] daysOfWeek = new Date[7];
        for (int i = 0; i < 7; i++) {
            daysOfWeek[i] = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return daysOfWeek;
    }


    private void setUpWeeklyBarChart() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
        SimpleDateFormat dayFormat = new SimpleDateFormat("EE", Locale.US);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());


        //add entries for the week
        for (int i = 0; i <= 6; i++) {
            Date startOfThisWeekDate = sdf.parse(sdf.format(cal.getTime()));
            RealmResults<Transaction> transactions = db.where(Transaction.class)
                    .equalTo("date",startOfThisWeekDate)
                    .notEqualTo("paymentType","Incoming Payment")
                    .equalTo("wallet",this.wallet)
                    .findAll();
            entries.add(new BarEntry((float)transactions.size(),i));
            labels.add(dayFormat.format(cal.getTime()));
            cal.add(Calendar.DATE,1);
        }

        BarDataSet dataSet = new BarDataSet(entries, "Number of Transactions");
        if(this.wallet.matches("ecocash")){
            dataSet.setColor(Color.BLUE);
        }else{
            dataSet.setColor(Color.RED);
        }
        BarData data = new BarData(labels, dataSet);
        mChart.setDescription("");
        mChart.setData(data);
    }
}