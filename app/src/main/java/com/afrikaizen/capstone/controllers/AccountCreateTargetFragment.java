package com.afrikaizen.capstone.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.models.Account;
import com.afrikaizen.capstone.models.NewActivity;
import com.afrikaizen.capstone.models.PaymentPlan;
import com.afrikaizen.capstone.singleton.AppBus;
import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by Steve on 12/4/2016.
 */
public class AccountCreateTargetFragment extends Fragment implements View.OnClickListener {

    Button startDate, endDate;
    EditText quantity;
    BetterSpinner selectServicePlan;
    TextView customerName, customerNumber;
    Account account;
    List<PaymentPlan> paymentPlans;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppBus.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account_create_target,container,false);

        startDate = (Button)rootView.findViewById(R.id.create_target_start_date);
        endDate = (Button)rootView.findViewById(R.id.create_target_end_date);
        quantity = (EditText)rootView.findViewById(R.id.create_target_quantity);
        selectServicePlan = (BetterSpinner) rootView.findViewById(R.id.create_target_select_service_plan);
        customerName = (TextView)rootView.findViewById(R.id.create_target_customer_name);
        customerNumber = (TextView)rootView.findViewById(R.id.create_target_customer_number);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity().getApplicationContext(),R.layout.spinner_item);
        for (PaymentPlan p : paymentPlans) {adapter.add(p.getPackageName()+" "+p.getAmount());}
        customerName.setText(account.getName());
        customerNumber.setText(account.getPhone());
        selectServicePlan.setAdapter(adapter);

        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        return rootView;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setPaymentPlans(List<PaymentPlan> paymentPlans) {
        this.paymentPlans = paymentPlans;
    }


    @Override
    public void onClick(View v) {
        AppBus.getInstance().post(new NewActivity(1));
    }
}
