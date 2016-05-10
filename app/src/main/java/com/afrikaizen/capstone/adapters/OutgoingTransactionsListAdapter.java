package com.afrikaizen.capstone.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.controllers.OutgoingTransactionsListFragment;
import com.afrikaizen.capstone.controllers.TransactionFragment;
import com.afrikaizen.capstone.models.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Steve on 9/5/2016.
 */
public class OutgoingTransactionsListAdapter extends RecyclerView.Adapter<OutgoingTransactionsListAdapter.TransactionListViewHolder>{

    OutgoingTransactionsListFragment f = null;
    ArrayList<Transaction> data =
            new ArrayList<Transaction>(Arrays.<Transaction>asList());

    public OutgoingTransactionsListAdapter(List<Transaction> data, OutgoingTransactionsListFragment f) {
        this.f = f;
        this.data.addAll(data);}

    public void setData(List<Transaction> data){
        this.data.addAll(data);
    }

    public void changeData(List<Transaction> data){
        this.data.clear();
        this.data.addAll(data);
    }

    public void addItem(Transaction t){
        this.data.add(t);
    }

    public List<Transaction> getData(){
        return this.data;
    }

    @Override
    public TransactionListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_outgoing_transaction_list, viewGroup, false);
        TransactionListViewHolder holder = new TransactionListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(OutgoingTransactionsListAdapter.TransactionListViewHolder viewHolder, int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        Transaction current = data.get(i);
        viewHolder.amount.setText(current.getAmount().toString());
        viewHolder.customerDetails.setText(current.getPhoneNumber());
        viewHolder.paymentType.setText(current.getPaymentType());
        viewHolder.details.setText(current.getDetails());
        viewHolder.date.setText(sdf.format(current.getDate()));
        viewHolder.time.setText(timeFormat.format(current.getTime()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class TransactionListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView time, paymentType, date, customerDetails, amount, details;
        public TransactionListViewHolder(View itemView) {
            super(itemView);
            time = (TextView)itemView.findViewById(R.id.time);
            paymentType = (TextView)itemView.findViewById(R.id.paymentType);
            date = (TextView)itemView.findViewById(R.id.date);
            customerDetails = (TextView)itemView.findViewById(R.id.customerDetails);
            amount = (TextView)itemView.findViewById(R.id.amount);
            details = (TextView)itemView.findViewById(R.id.details);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition(); // gets item position
            Transaction t = data.get(position);
            f.handleClick(t);
        }
    }
}
