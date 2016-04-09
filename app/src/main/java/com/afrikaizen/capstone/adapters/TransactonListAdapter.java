package com.afrikaizen.capstone.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.models.PaymentPlan;
import com.afrikaizen.capstone.models.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Steve on 21/3/2016.
 */
public class TransactonListAdapter extends RecyclerView.Adapter<TransactonListAdapter.TransactionListViewHolder>
    implements View.OnClickListener{

    ArrayList<Transaction> data =
            new ArrayList<Transaction>(Arrays.<Transaction>asList());

    public TransactonListAdapter(List<Transaction> data) {this.data.addAll(data);}

    public void setData(List<Transaction> data){
        this.data.addAll(data);
    }

    public void changeData(List<Transaction> data){
        this.data.removeAll(this.data);
        this.data.addAll(data);
    }

    public List<Transaction> getData(){
     return this.data;
    }

    @Override
    public TransactionListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_transaction_list, viewGroup, false);
        TransactionListViewHolder holder = new TransactionListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TransactonListAdapter.TransactionListViewHolder viewHolder, int i) {
        Transaction current = data.get(i);
        viewHolder.confirmaionCode.setText(current.getConfirmaionCode());
        viewHolder.amount.setText(current.getAmount());
        viewHolder.customerDetails.setText(current.getCustomerDetails());
        viewHolder.paymentType.setText(current.getPaymentType());
        viewHolder.details.setText(current.getDetails());
        viewHolder.date.setText(current.getDate());
        viewHolder.time.setText(current.getTime());
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class TransactionListViewHolder extends RecyclerView.ViewHolder{
        TextView time, paymentType, date, customerDetails, amount, details, confirmaionCode;
        public TransactionListViewHolder(View itemView) {
            super(itemView);
            time = (TextView)itemView.findViewById(R.id.time);
            paymentType = (TextView)itemView.findViewById(R.id.paymentType);
            date = (TextView)itemView.findViewById(R.id.date);
            customerDetails = (TextView)itemView.findViewById(R.id.customerDetails);
            amount = (TextView)itemView.findViewById(R.id.amount);
            details = (TextView)itemView.findViewById(R.id.details);
            confirmaionCode = (TextView)itemView.findViewById(R.id.confirmaionCode);
        }
    }
}
