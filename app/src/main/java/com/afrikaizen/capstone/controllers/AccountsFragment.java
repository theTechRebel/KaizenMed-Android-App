package com.afrikaizen.capstone.controllers;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.adapters.AccountListAdapter;
import com.afrikaizen.capstone.adapters.PaymentPlanListAdapter;
import com.afrikaizen.capstone.imports.DividerItemDecoration;
import com.afrikaizen.capstone.models.Account;
import com.afrikaizen.capstone.models.NewActivity;
import com.afrikaizen.capstone.singleton.AppBus;

import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;
import net.i2p.android.ext.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 31/3/2016.
 */
public class AccountsFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private AccountListAdapter adapter;
    private RecyclerView.ItemDecoration recyclerItemDecoration;
    List<Account> data;
    Account a;

    FloatingActionsMenu menuMultipleActions;
    FloatingActionButton actionA,actionB;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_accounts, container, false);

        data = new ArrayList<Account>();

        recyclerView = (RecyclerView)rootView.findViewById(R.id.payment_plans_list);
        adapter = new AccountListAdapter(getData());
        data.addAll(getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerItemDecoration  = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(recyclerItemDecoration);

        menuMultipleActions = (FloatingActionsMenu) rootView.findViewById(R.id.multiple_actions);

        actionA = (FloatingActionButton) rootView.findViewById(R.id.action_a);
        actionA.setTitle("Add new customer account");
        actionB = (FloatingActionButton) rootView.findViewById(R.id.action_a);
        actionB.setTitle("Add new contact");

        actionA.setOnClickListener(this);
        actionB.setOnClickListener(this);
        return rootView;
    }

    private static List<Account> getData(){
        List<Account> data = new ArrayList();

        String name[] = {"Steve", "Simba","Eugene","Tendai"};
        String surname[] = {"Dumbura","Murotwa","Mhundure","Mutenha"};
        String email[] = {"stevedumbura@gmail.com","simbarashemurotwa@gmail.com","eyzee@gmail.com","tmutenha@gmail.com"};
        String phone[] = {"0777902073","077890036","0786543234","07984563450"};

        for (int i=0; i<name.length;i++){
            Account acc = new Account();
            acc.setEmail(email[i]);
            acc.setName(name[i]);
            acc.setSurname(surname[i]);
            acc.setPhone(phone[i]);
            data.add(acc);
        }

        return data;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //TODO: Add search view to filter in app contacts "accounts" by search query
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.action_a:
                AppBus.getInstance().post(new NewActivity(0));
                break;
            case R.id.action_b:
                //TODO: Make this button add new contact to the existing contacts
                Toast.makeText(getActivity(), "Floating Action Button Clicked" , Toast.LENGTH_LONG).show();
                break;
        }
    }
}
