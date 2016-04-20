package com.afrikaizen.capstone.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.controllers.AccountTargetFragment;
import com.afrikaizen.capstone.models.Account;
import com.afrikaizen.capstone.models.Target;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Steve on 29/3/2016.
 */
public class AccountTargetListAdapter extends RecyclerView.Adapter<AccountTargetListAdapter.AccountTargetViewHolder> implements View.OnClickListener{
    AccountTargetFragment f = null;
    ArrayList<Target> data =
            new ArrayList<Target>(Arrays.<Target>asList());
    Account a;
    TextView dateCreated, paymentType, customer, planName, plan, quantity, startDateEndDate;

    public AccountTargetListAdapter(List<Target> data,Account a, AccountTargetFragment f) {
        this.f = f;
        this.data.addAll(data);
        this.a = new Account();
        this.a = a;
    }

    public void setData(List<Target> data){
        this.data.addAll(data);
    }

    public void changeData(List<Target> data){
        this.data.removeAll(this.data);
        this.data.addAll(data);
    }

    @Override
    public AccountTargetViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_target_list,viewGroup,false);

        dateCreated = (TextView)v.findViewById(R.id.dateCreated);
        paymentType = (TextView)v.findViewById(R.id.paymentType);
        customer = (TextView)v.findViewById(R.id.customer);
        planName = (TextView)v.findViewById(R.id.planName);
        plan = (TextView)v.findViewById(R.id.plan);
        quantity = (TextView)v.findViewById(R.id.quantity);
        startDateEndDate = (TextView)v.findViewById(R.id.startDateEndDate);

        v.setOnClickListener(this);
        AccountTargetViewHolder holder = new AccountTargetViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(AccountTargetViewHolder accountTargetViewHolder, int i) {
        Target acc = data.get(i);
        String recurring = "NOT RECURRING";
        if(acc.getRequiring()){
            recurring = "RECURRING";
        }
        accountTargetViewHolder.dateCreated.setText(acc.getDateCreated().toString());
        accountTargetViewHolder.paymentType.setText(acc.getPaymentType());
        accountTargetViewHolder.customer.setText(a.getName()+" "+a.getPhone());
        accountTargetViewHolder.planName.setText(acc.getPlan().getPackageName());
        accountTargetViewHolder.plan.setText("$"+acc.getPlan().getAmount()+" / "+acc.getPlan().getPeriod()+" days "+recurring);
        accountTargetViewHolder.quantity.setText(acc.getQuantity()+" x ");
        accountTargetViewHolder.startDateEndDate.setText(acc.getStartDate()+" - "+acc.getEndDate());
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {
        View v1 = (View)v.getParent().getParent().getParent();
        Target a = this.data.get(f.getRecyclerView().getChildLayoutPosition(v1));
        //f.onClick(fragment);
    }

    public void addItem(Target a){
        this.data.add(a);
    }

    public void removeItem(int i){
        this.data.remove(i);
    }

    class AccountTargetViewHolder extends RecyclerView.ViewHolder{
        TextView dateCreated, paymentType, customer, planName, plan, quantity, startDateEndDate;

        public AccountTargetViewHolder(View itemView) {
            super(itemView);
            dateCreated = (TextView)itemView.findViewById(R.id.dateCreated);
            paymentType = (TextView)itemView.findViewById(R.id.paymentType);
            customer = (TextView)itemView.findViewById(R.id.customer);
            planName = (TextView)itemView.findViewById(R.id.planName);
            plan = (TextView)itemView.findViewById(R.id.plan);
            quantity = (TextView)itemView.findViewById(R.id.quantity);
            startDateEndDate = (TextView)itemView.findViewById(R.id.startDateEndDate);
        }
    }
}
