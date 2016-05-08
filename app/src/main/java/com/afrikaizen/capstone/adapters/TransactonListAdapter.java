package com.afrikaizen.capstone.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.controllers.TransactionFragment;
import com.afrikaizen.capstone.models.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Steve on 21/3/2016.
 */
public class TransactonListAdapter extends RecyclerView.Adapter<TransactonListAdapter.TransactionListViewHolder>{

    TransactionFragment f = null;
    ArrayList<Transaction> data =
            new ArrayList<Transaction>(Arrays.<Transaction>asList());

    public TransactonListAdapter(List<Transaction> data, TransactionFragment f) {
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
                .inflate(R.layout.item_transaction_list, viewGroup, false);
        TransactionListViewHolder holder = new TransactionListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TransactonListAdapter.TransactionListViewHolder viewHolder, int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        Transaction current = data.get(i);
        if(current.getAccountNumber()==null || current.getAccountNumber().isEmpty()){
            viewHolder.labelaccnum.setText("");
        }
        viewHolder.confirmaionCode.setText(current.getConfirmaionCode());
        viewHolder.amount.setText(current.getAmount().toString());
        viewHolder.customerDetails.setText(current.getCustomerDetails());
        viewHolder.paymentType.setText(current.getPaymentType());
        viewHolder.details.setText(current.getDetails());
        viewHolder.date.setText(sdf.format(current.getDate()));
        viewHolder.time.setText(timeFormat.format(current.getTime()));
        viewHolder.accountnumber.setText(current.getAccountNumber());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class TransactionListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView time, paymentType, date, customerDetails, amount, details, confirmaionCode,accountnumber,labelaccnum;
        public TransactionListViewHolder(View itemView) {
            super(itemView);
            labelaccnum = (TextView)itemView.findViewById(R.id.label_account_number);
            time = (TextView)itemView.findViewById(R.id.time);
            paymentType = (TextView)itemView.findViewById(R.id.paymentType);
            date = (TextView)itemView.findViewById(R.id.date);
            customerDetails = (TextView)itemView.findViewById(R.id.customerDetails);
            amount = (TextView)itemView.findViewById(R.id.amount);
            details = (TextView)itemView.findViewById(R.id.details);
            confirmaionCode = (TextView)itemView.findViewById(R.id.confirmaionCode);
            accountnumber = (TextView)itemView.findViewById(R.id.accountnumber);
            labelaccnum = (TextView)itemView.findViewById(R.id.label_account_number);

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
