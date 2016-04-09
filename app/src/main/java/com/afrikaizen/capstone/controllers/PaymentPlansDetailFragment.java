package com.afrikaizen.capstone.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.models.NewActivity;
import com.afrikaizen.capstone.models.PaymentPlan;
import com.afrikaizen.capstone.orm.RealmService;
import com.afrikaizen.capstone.singleton.AppBus;

import io.realm.Realm;

/**
 * Created by Steve on 9/4/2016.
 */
public class PaymentPlansDetailFragment extends Fragment implements View.OnClickListener {
    EditText payment_plan_title,payment_plan_description,payment_plan_amount,payment_plan_payback_period;
    Button payment_plan_save, payment_plan_clear;
    Realm db;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppBus.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_payment_plans_detail, container, false);

        db = RealmService.getInstance(getActivity().getApplication()).getRealm();

        payment_plan_title = (EditText) rootView.findViewById(R.id.payment_plan_title);
        payment_plan_description = (EditText) rootView.findViewById(R.id.payment_plan_description);
        payment_plan_amount = (EditText) rootView.findViewById(R.id.payment_plan_amount);
        payment_plan_payback_period = (EditText) rootView.findViewById(R.id.payment_plan_payback_period);
        payment_plan_save = (Button)rootView.findViewById(R.id.payment_plan_save);
        payment_plan_clear = (Button)rootView.findViewById(R.id.payment_plan_clear);

        payment_plan_save.setOnClickListener(this);
        payment_plan_clear.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.payment_plan_clear:
                payment_plan_title.setText("");
                payment_plan_description.setText("");
                payment_plan_amount.setText("");
                payment_plan_payback_period.setText("");
                break;
            case R.id.payment_plan_save:
                PaymentPlan p = new PaymentPlan();
                p.setPackageName(payment_plan_title.getText().toString());
                p.setDescription(payment_plan_description.getText().toString());
                p.setAmount(Double.parseDouble(payment_plan_amount.getText().toString()));
                p.setPeriod(Integer.parseInt(payment_plan_payback_period.getText().toString()));

                db.beginTransaction();
                db.copyToRealmOrUpdate(p);
                db.commitTransaction();

                AppBus.getInstance().post(new NewActivity(0));
                break;
        }
    }
}
