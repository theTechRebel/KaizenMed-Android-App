package com.afrikaizen.capstone.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.models.CustomerAccount;
import com.afrikaizen.capstone.models.PaymentPlan;
import com.afrikaizen.capstone.models.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Steve on 23/4/2016.
 */
public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.TransactionPaymentHistoryViewHolder>{
    ArrayList<CustomerAccount> data =
            new ArrayList<CustomerAccount>(Arrays.<CustomerAccount>asList());

    public PaymentHistoryAdapter(List<CustomerAccount> data){
        this.data.addAll(data);
    }

    @Override
    public TransactionPaymentHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction_history,parent,false);
        TransactionPaymentHistoryViewHolder holder = new TransactionPaymentHistoryViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(PaymentHistoryAdapter.TransactionPaymentHistoryViewHolder holder, int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
        CustomerAccount customerAccount = this.data.get(position);
        Transaction t = customerAccount.getTransaction();
        PaymentPlan p = customerAccount.getPaymentPlan();
        Double paymentToDate = p.getAmount() - customerAccount.getAmountLeft();

        holder.date.setText(sdf.format(t.getDate()));
        //holder.paymentType.setText(t.getPaymentType());
        holder.confirmationCode.setText("Confirmation Code: "+t.getConfirmaionCode());
        holder.packageName.setText("Payment Plan: "+t.getDetails());
        holder.totalCost.setText(p.getAmount().toString());
        holder.paymentToDate.setText(paymentToDate.toString());
        holder.outstandingBalance.setText(customerAccount.getAmountLeft().toString());
        holder.amount.setText(t.getAmount().toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class TransactionPaymentHistoryViewHolder extends RecyclerView.ViewHolder{

        TextView date,paymentType,totalCost,paymentToDate,outstandingBalance,confirmationCode,packageName,amount;

        public TransactionPaymentHistoryViewHolder(View v) {
            super(v);
            amount = (TextView)v.findViewById(R.id.transaction_history_amount);
            date = (TextView)v.findViewById(R.id.date);
            //paymentType = (TextView)v.findViewById(R.id.paymentType);
            totalCost = (TextView)v.findViewById(R.id.transaction_history_total_cost);
            paymentToDate = (TextView)v.findViewById(R.id.transaction_history_payment_to_date);
            outstandingBalance = (TextView)v.findViewById(R.id.transaction_history_outstanding);
            confirmationCode = (TextView)v.findViewById(R.id.transaction_confirmation_code);
            packageName = (TextView)v.findViewById(R.id.transaction_package);
        }
    }
}
