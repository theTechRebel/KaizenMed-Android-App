package com.afrikaizen.capstone.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.models.Account;
import com.afrikaizen.capstone.models.PaymentPlan;

import java.util.Collections;
import java.util.List;

/**
 * Created by Steve on 29/3/2016.
 */
public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.AccountViewHolder> implements View.OnClickListener{

    List<Account> data = Collections.emptyList();

    public AccountListAdapter(List<Account> data) {this.data = data;}

    public void setData(List<Account> data){
        this.data.addAll(data);
    }

    public void changeData(List<Account> data){
        this.data.removeAll(this.data);
        this.data.addAll(data);
    }

    @Override
    public AccountViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_account_list,viewGroup,false);
        AccountViewHolder holder = new AccountViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(AccountViewHolder accountViewHolder, int i) {
        Account acc = data.get(i);
        accountViewHolder.email.setText(acc.getEmail());
        accountViewHolder.name.setText(acc.getName());
        accountViewHolder.surname.setText(acc.getSurname());
        accountViewHolder.phone.setText(acc.getPhone());
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {

    }

    class AccountViewHolder extends RecyclerView.ViewHolder{
        TextView name, surname, email, phone;

        public AccountViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            surname = (TextView)itemView.findViewById(R.id.surame);
            email = (TextView)itemView.findViewById(R.id.email);
            phone = (TextView)itemView.findViewById(R.id.phone);
        }
    }
}
