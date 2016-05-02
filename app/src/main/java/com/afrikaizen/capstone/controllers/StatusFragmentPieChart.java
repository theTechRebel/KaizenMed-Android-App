package com.afrikaizen.capstone.controllers;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.models.Wallet;
import com.afrikaizen.capstone.orm.RealmService;
import com.afrikaizen.capstone.singleton.AppPreferences;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Steve on 19/3/2016.
 */
public class StatusFragmentPieChart extends Fragment{
    PieChart pieChart;
    TextView ecocashStatus,telecashStatus,ecocashAmount,telecashAmount;
    //private Typeface tf;
    Realm db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_status_pie_chart, container, false);
        pieChart = (PieChart) rootView.findViewById(R.id.pieChart);
        ecocashAmount = (TextView)rootView.findViewById(R.id.status_ecocash);
        telecashAmount = (TextView)rootView.findViewById(R.id.status_telecel);
        ecocashStatus = (TextView)rootView.findViewById(R.id.status_active_ecocash);
        telecashStatus = (TextView)rootView.findViewById(R.id.status_active_telecel);

        db = RealmService.getInstance(getActivity().getApplication()).getRealm();
        initialisePieChart();
        return rootView;
    }

    private void initialisePieChart(){
        pieChart.setCenterTextSize(10f);

        // radius of the center hole in percent of maximum radius
        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleRadius(45f);

        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        pieChart.setUsePercentValues(true);
        pieChart.setRotationEnabled(true);
        generatePieData();
    }

    private SpannableString generateCenterText(Double total) {
        SpannableString s = new SpannableString("$"+total.toString()+" \n Balance");
        s.setSpan(new RelativeSizeSpan(2f), 0, 8, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, s.length(), 0);
        return s;
    }

    protected void generatePieData() {
        RealmResults<Wallet> listOfWallets = db.where(Wallet.class)
                .findAll();
        int count = listOfWallets.size();

        pieChart.setCenterText(generateCenterText(listOfWallets.get(0).getBalance()+listOfWallets.get(1).getBalance()));
        float[] yData = { Float.parseFloat(listOfWallets.get(0).getBalance().toString()),
                          Float.parseFloat(listOfWallets.get(1).getBalance().toString())};
        ecocashAmount.setText("$"+listOfWallets.get(0).getBalance().toString());
        telecashAmount.setText("$"+listOfWallets.get(1).getBalance().toString());
        if(AppPreferences.getInstance(getActivity().getApplicationContext()).getEcoCashWallet()==
                AppPreferences.getInstance(getActivity().getApplicationContext()).DEFAULT_VALUE_STRING){
            ecocashStatus.setText("DEACTIVATED");
        }
        if(AppPreferences.getInstance(getActivity().getApplicationContext()).getTeleCashWallet()==
                AppPreferences.getInstance(getActivity().getApplicationContext()).DEFAULT_VALUE_STRING){
            telecashStatus.setText("DEACTIVATED");
        }
        String[] xData = { "EcoCash: "+listOfWallets.get(0).getWalletName(), "TeleCash: "+listOfWallets.get(1).getWalletName()};

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yData.length; i++)
            yVals1.add(new Entry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(20f);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        // update pie chart
        pieChart.invalidate();
    }
}
