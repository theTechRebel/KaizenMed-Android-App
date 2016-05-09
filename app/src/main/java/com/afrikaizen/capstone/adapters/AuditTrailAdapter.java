package com.afrikaizen.capstone.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.models.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Steve on 9/5/2016.
 */
public class AuditTrailAdapter extends ArrayAdapter<Transaction> {
    // View lookup cache
    Context ctx;

    private static class ViewHolder {
        TextView id,date,time,paymentType,amount,phoneNumber,code,wallet;
    }

    public AuditTrailAdapter(Context context, ArrayList<Transaction> transactions) {
        super(context, R.layout.item_audit_trail, transactions);
        this.ctx = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        Transaction transaaction = (Transaction)getItem(position);
        ViewHolder viewHolder = null;

        LayoutInflater mInflater = (LayoutInflater) ctx.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_audit_trail, parent, false);
            viewHolder.id = (TextView) convertView.findViewById(R.id.transactionId);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            viewHolder.paymentType = (TextView) convertView.findViewById(R.id.paymentType);
            viewHolder.amount = (TextView) convertView.findViewById(R.id.amount);
            viewHolder.phoneNumber = (TextView) convertView.findViewById(R.id.phoneNumber);
            viewHolder.wallet = (TextView) convertView.findViewById(R.id.wallet);
            viewHolder.code = (TextView) convertView.findViewById(R.id.confirmaionCode);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        viewHolder.id.setText("ID "+transaaction.getId());
        viewHolder.date.setText(sdf.format(transaaction.getDate()));
        viewHolder.time.setText(timeFormat.format(transaaction.getTime()));
        viewHolder.paymentType.setText(transaaction.getPaymentType());
        viewHolder.amount.setText("$"+transaaction.getAmount());
        viewHolder.phoneNumber.setText(transaaction.getPhoneNumber());
        viewHolder.wallet.setText(transaaction.getWallet());
        viewHolder.code.setText(transaaction.getConfirmaionCode());


        return convertView;
    }
}
