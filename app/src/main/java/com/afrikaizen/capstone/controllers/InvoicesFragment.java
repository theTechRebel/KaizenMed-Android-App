package com.afrikaizen.capstone.controllers;

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
import com.afrikaizen.capstone.adapters.InvoiceListAdapter;
import com.afrikaizen.capstone.adapters.TransactonListAdapter;
import com.afrikaizen.capstone.models.Invoices;
import com.afrikaizen.capstone.models.Transaction;
import com.afrikaizen.capstone.orm.RealmService;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

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
 * Created by Steve on 8/5/2016.
 */
public class InvoicesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    String wallet;

    private RecyclerView recyclerView;
    private InvoiceListAdapter adapter;
    private RecyclerView.ItemDecoration recyclerItemDecoration;
    private SwipeRefreshLayout swipeToRefresh;
    ArrayList<Invoices> data =
            new ArrayList<Invoices>(Arrays.<Invoices>asList());
    Invoices i;
    RealmResults<Invoices> result;
    RealmQuery<Invoices> query;
    Realm db;

    protected BarChart mChart;
    ArrayList<BarEntry> entries = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transactions, container, false);

        Bundle b = getArguments();
        wallet = b.getString("WALLET");

        db = RealmService.getInstance(getActivity().getApplication()).getRealm();
        mChart = (BarChart) rootView.findViewById(R.id.chart1);

        try {
            setUpWeeklyBarChart();
        } catch (ParseException e) {
            Log.d("BAR_CHART_ERROR", e.toString());
        }

        data = new ArrayList<Invoices>();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.transaction_list);
        adapter = new InvoiceListAdapter(getData("*"), this);
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


    private List<Invoices> getData(String params) {
        List<Invoices> data = new ArrayList();
        if (params == "*") {
            query = db.where(Invoices.class)
                    .equalTo("wallet", this.wallet);
            result = query.findAll();
            data.addAll(result);
        }
        return data;
    }

    private void setUpWeeklyBarChart() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
        SimpleDateFormat dayFormat = new SimpleDateFormat("EE", Locale.US);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());


        //add entries for the week
        for (int i = 0; i <= 6; i++) {
            Date startOfThisWeekDate = sdf.parse(sdf.format(cal.getTime()));
            RealmResults<Invoices> transactions = db.where(Invoices.class)
                    .equalTo("date",startOfThisWeekDate)
                    .equalTo("wallet",this.wallet)
                    .findAll();
            entries.add(new BarEntry((float)transactions.size(),i));
            labels.add(dayFormat.format(cal.getTime()));
            cal.add(Calendar.DATE,1);
        }

        BarDataSet dataSet = new BarDataSet(entries, "Number of Invoices");
        if(this.wallet.matches("ecocash")){
            dataSet.setColor(Color.BLUE);
        }else{
            dataSet.setColor(Color.RED);
        }
        BarData data = new BarData(labels, dataSet);
        mChart.setDescription("");
        mChart.setData(data);
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
                        swipeToRefresh.setRefreshing(false);
                    }
                },3000);
            }
        });
    }
}
