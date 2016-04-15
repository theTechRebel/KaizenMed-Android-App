package com.afrikaizen.capstone.adapters;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.controllers.AccountCreateTargetFragment;
import com.afrikaizen.capstone.controllers.AccountsFragment;
import com.afrikaizen.capstone.models.Account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Steve on 29/3/2016.
 */
public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.AccountViewHolder> implements View.OnClickListener{
    AccountsFragment f = null;
    ArrayList<Account> data =
            new ArrayList<Account>(Arrays.<Account>asList());
    TextView account_view,account_start,account_edit;

    public AccountListAdapter(List<Account> data, AccountsFragment f) {
        this.f = f;
        this.data.addAll(data);
    }

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

        account_view = (TextView)v.findViewById(R.id.account_view);
        account_start = (TextView)v.findViewById(R.id.account_start);
        account_edit = (TextView)v.findViewById(R.id.account_edit);

        account_view.setOnClickListener(this);
        account_start.setOnClickListener(this);
        account_edit.setOnClickListener(this);

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
        View v1 = (View)v.getParent().getParent().getParent();
        Account a = this.data.get(f.getRecyclerView().getChildLayoutPosition(v1));

        switch(v.getId()){
            case R.id.account_view:

                break;

            case R.id.account_start:
                AccountCreateTargetFragment fragment = new AccountCreateTargetFragment();
                Bundle b = new Bundle();
                b.putString("TAG","CREATE_TARGET");
                fragment.setArguments(b);
                fragment.setAccount(a);
                f.onClick(fragment);
                break;

            case R.id.account_edit:

                break;
        }



    }

    public void addItem(Account a){
        this.data.add(a);
    }

    public void removeItem(int i){
        this.data.remove(i);
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
