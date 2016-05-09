package com.afrikaizen.capstone.controllers;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.adapters.AuditTrailAdapter;
import com.afrikaizen.capstone.models.Account;
import com.afrikaizen.capstone.models.Transaction;
import com.afrikaizen.capstone.orm.RealmService;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Steve on 9/5/2016.
 */
public class AuditTrailFragment extends Fragment implements View.OnClickListener, CalendarDatePickerDialogFragment.OnDateSetListener {
    Button start,end;
    TextView startTv,endTv;
    private static final String FRAG_TAG_DATE_PICKER_1 = "DATE_1";
    private static final String FRAG_TAG_DATE_PICKER_2 = "DATE_2";
    ListView listView;
    AuditTrailAdapter adapter;
    Date d1,d2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_audit_trail,container,false);
        listView = (ListView)v.findViewById(R.id.transactionsList);
        adapter = new AuditTrailAdapter(getActivity().getApplicationContext(),getData());
        listView.setAdapter(adapter);
        start = (Button)v.findViewById(R.id.audit_trail_start);
        end = (Button)v.findViewById(R.id.audit_trail_end);
        startTv = (TextView)v.findViewById(R.id.startTV);
        endTv = (TextView)v.findViewById(R.id.endTv);
        start.setOnClickListener(this);
        end.setOnClickListener(this);
        return v;
    }


    private ArrayList<Transaction> getData(){
        ArrayList<Transaction> data =
                new ArrayList<Transaction>(Arrays.<Transaction>asList());
            Realm db = RealmService.getInstance(getActivity().getApplication()).getRealm();
            RealmResults<Transaction> results = db.where(Transaction.class)
                    .findAll();
            data.addAll(results);
        return data;
    }
    private ArrayList<Transaction> getData(Date d1, Date d2){
        ArrayList<Transaction> data =
                new ArrayList<Transaction>(Arrays.<Transaction>asList());
        Realm db = RealmService.getInstance(getActivity().getApplication()).getRealm();
        RealmResults<Transaction> results = db.where(Transaction.class).between("date",d1,d2).findAll();
        data.addAll(results);
        return data;
    }

    @Override
    public void onClick(View v) {
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(this);
        switch(v.getId()){
            case R.id.audit_trail_start:
                cdp.show(getActivity().getSupportFragmentManager(), FRAG_TAG_DATE_PICKER_1);
                break;
            case R.id.audit_trail_end:
                cdp.show(getActivity().getSupportFragmentManager(), FRAG_TAG_DATE_PICKER_2);
                break;
        }
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
        Calendar c = new GregorianCalendar(year,monthOfYear,dayOfMonth);
        String date = sdf.format(c.getTime());
        switch(dialog.getTag()){
            case FRAG_TAG_DATE_PICKER_1:
                d1 = c.getTime();
                startTv.setText(date);
                adapter.clear();
                adapter.addAll(getData());
                listView.invalidateViews();
                break;
            case FRAG_TAG_DATE_PICKER_2:
                d2 = c.getTime();
                endTv.setText(date);
                adapter.clear();
                adapter.addAll(getData(d1,d2));
                listView.invalidateViews();
                break;
        }
    }
}
