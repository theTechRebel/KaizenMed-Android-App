package com.afrikaizen.capstone.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.models.Account;
import com.afrikaizen.capstone.models.PaymentPlan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Steve on 29/3/2016.
 */
public class PaymentPlanListAdapter extends RecyclerView.Adapter<PaymentPlanListAdapter.PaymentPlanViewHolder> implements View.OnClickListener{

    ArrayList<PaymentPlan> data =
            new ArrayList<PaymentPlan>(Arrays.<PaymentPlan>asList());

    public PaymentPlanListAdapter(List<PaymentPlan> data) {this.data.addAll(data);}

    public void setData(List<PaymentPlan> data){
        this.data.addAll(data);
    }

    public void changeData(List<PaymentPlan> data){
        this.data.removeAll(this.data);
        this.data.addAll(data);
    }

    @Override
    public PaymentPlanViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_payment_plan_list,viewGroup,false);
        PaymentPlanViewHolder holder = new PaymentPlanViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(PaymentPlanViewHolder paymentPlanViewHolder, int i) {
        PaymentPlan current = data.get(i);
        paymentPlanViewHolder.amount.setText("$"+current.getAmount().toString());
        paymentPlanViewHolder.description.setText(current.getDescription());
        paymentPlanViewHolder.packageName.setText(current.getPackageName());
        paymentPlanViewHolder.period.setText(current.getPeriod()+" Days");
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {

    }

    class PaymentPlanViewHolder extends RecyclerView.ViewHolder{
        TextView packageName, description, period, amount;

        public PaymentPlanViewHolder(View itemView) {
            super(itemView);
            packageName = (TextView)itemView.findViewById(R.id.packageName);
            description = (TextView)itemView.findViewById(R.id.description);
            period = (TextView)itemView.findViewById(R.id.period);
            amount = (TextView)itemView.findViewById(R.id.amount);
        }
    }
}
