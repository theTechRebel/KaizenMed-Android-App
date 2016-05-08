package com.afrikaizen.capstone.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.controllers.InvoicesFragment;
import com.afrikaizen.capstone.controllers.TransactionFragment;
import com.afrikaizen.capstone.models.Invoices;
import com.afrikaizen.capstone.models.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Steve on 8/5/2016.
 */
public class InvoiceListAdapter extends RecyclerView.Adapter<InvoiceListAdapter.InvoicesListViewHolder>{

    InvoicesFragment f = null;
    ArrayList<Invoices> data =
            new ArrayList<Invoices>(Arrays.<Invoices>asList());

    public InvoiceListAdapter(List<Invoices> data, InvoicesFragment f) {
        this.f = f;
        this.data.addAll(data);}

    public void setData(List<Invoices> data){
        this.data.addAll(data);
    }

    public void changeData(List<Invoices> data){
        this.data.clear();
        this.data.addAll(data);
    }

    public void addItem(Invoices t){
        this.data.add(t);
    }

    public List<Invoices> getData(){
        return this.data;
    }

    @Override
    public InvoicesListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_invoice_list, viewGroup, false);
        InvoicesListViewHolder holder = new InvoicesListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(InvoiceListAdapter.InvoicesListViewHolder viewHolder, int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        Invoices current = data.get(i);
        Double amount = current.getPrice()*current.getQuantity();

        viewHolder.phone.setText(current.getPhonenumber());
        viewHolder.amount.setText(amount.toString());
        viewHolder.invoicenumber.setText(current.getInvoiceNumber());
        viewHolder.item.setText(current.getItem());
        viewHolder.customer.setText(current.getCustomername());
        viewHolder.date.setText(sdf.format(current.getDate()));
        viewHolder.time.setText(timeFormat.format(current.getTime()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class InvoicesListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView time, phone, date, customer, amount, invoicenumber,item;
        public InvoicesListViewHolder(View itemView) {
            super(itemView);
            time = (TextView)itemView.findViewById(R.id.time);
            date = (TextView)itemView.findViewById(R.id.date);
            customer = (TextView)itemView.findViewById(R.id.customerDetails);
            amount = (TextView)itemView.findViewById(R.id.amount);
            invoicenumber = (TextView)itemView.findViewById(R.id.invoicenumber);
            item = (TextView)itemView.findViewById(R.id.item);
            phone = (TextView)itemView.findViewById(R.id.phonenumber);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition(); // gets item position
            Invoices t = data.get(position);
        }
    }
}
