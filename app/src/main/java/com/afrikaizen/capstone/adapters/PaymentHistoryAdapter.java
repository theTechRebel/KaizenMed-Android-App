package com.afrikaizen.capstone.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.models.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Steve on 23/4/2016.
 */
public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.TransactionPaymentHistoryViewHolder>{
    ArrayList<Transaction> data =
            new ArrayList<Transaction>(Arrays.<Transaction>asList());

    public PaymentHistoryAdapter(List<Transaction> data){
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
        Transaction t = this.data.get(position);
        holder.date.setText(sdf.format(t.getDate()));
        holder.paymentType.setText(t.getPaymentType());
        holder.confirmationCode.setText(t.getConfirmaionCode());
        holder.packageName.setText(t.getDetails());
        holder.totalCost.setText("0");
        holder.paymentToDate.setText("0");
        holder.outstandingBalance.setText("0");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class TransactionPaymentHistoryViewHolder extends RecyclerView.ViewHolder{

        TextView date,paymentType,totalCost,paymentToDate,outstandingBalance,confirmationCode,packageName;

        public TransactionPaymentHistoryViewHolder(View v) {
            super(v);
            date = (TextView)v.findViewById(R.id.date);
            paymentType = (TextView)v.findViewById(R.id.paymentType);
            totalCost = (TextView)v.findViewById(R.id.transaction_history_total_cost);
            paymentToDate = (TextView)v.findViewById(R.id.transaction_history_payment_to_date);
            outstandingBalance = (TextView)v.findViewById(R.id.transaction_history_outstanding);
            confirmationCode = (TextView)v.findViewById(R.id.transaction_confirmation_code);
            packageName = (TextView)v.findViewById(R.id.transaction_package);
        }
    }
}
