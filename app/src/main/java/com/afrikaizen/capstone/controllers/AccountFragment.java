package com.afrikaizen.capstone.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.adapters.AccountListAdapter;
import com.afrikaizen.capstone.models.Account;
import com.afrikaizen.capstone.models.PaymentPlan;
import com.afrikaizen.capstone.orm.RealmService;
import com.afrikaizen.capstone.singleton.AppBus;
import com.codinguser.android.contactpicker.ContactsPickerActivity;

import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Steve on 31/3/2016.
 */
public class AccountFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private AccountListAdapter adapter;
    private RecyclerView.ItemDecoration recyclerItemDecoration;
    RealmResults<Account> result;
    RealmQuery<Account> query;
    List<Account> data;
    Account a;
    Realm db;

    FloatingActionButton contacts;
    private static final int GET_PHONE_NUMBER = 3007;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppBus.getInstance().register(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_accounts, container, false);
        db = RealmService.getInstance(getActivity().getApplication()).getRealm();

        data = new ArrayList<Account>();
        adapter = new AccountListAdapter(getData("*"),this);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.payment_plans_list);
        data.addAll(getData("*"));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        //recyclerItemDecoration  = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        //recyclerView.addItemDecoration(recyclerItemDecoration);


        contacts = (FloatingActionButton) rootView.findViewById(R.id.contacts);
        contacts.setTitle("Add new customer account");
        contacts.setOnClickListener(this);
        return rootView;
    }

    private List<Account> getData(String params){
        if(params == "*"){
            query = db.where(Account.class);
            result = query.findAll();
        }else{
            query = db.where(Account.class);
            result = db.where(Account.class)
                    .equalTo("phone", params)
                    .findAll();
        }

        return result;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //TODO: Add search view to filter in app contacts "accounts" by search query
    }

    //contacts floating action button click
    @Override
    public void onClick(View v){
        startActivityForResult(new Intent(getActivity(),ContactsPickerActivity.class),GET_PHONE_NUMBER);
    }

    //result of the floating action button click
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // See which child activity is calling us back.
        switch (requestCode) {
            case GET_PHONE_NUMBER:
                // This is the standard resultCode that is sent back if the
                // activity crashed or didn't doesn't supply an explicit result.
                if (resultCode == getActivity().RESULT_CANCELED) {
                    Toast.makeText(getActivity(), "No contact selected.", Toast.LENGTH_LONG).show();
                } else {
                    String phoneNumber = (String) data.getExtras().get(ContactsPickerActivity.KEY_PHONE_NUMBER);
                    String contactName = (String) data.getExtras().get(ContactsPickerActivity.KEY_CONTACT_NAME);

                    String phone = phoneNumber.replaceAll("[^0-9]", "");

                    String twoSixThree = phone.substring(0,3);
                    char first = phone.charAt(0);

                    Account a = new Account();

                    if(first == '0'){
                        if(phone.length() < 10){
                            Log.d("WALLET",phone+" | Length of phone number is too small");
                            return;
                        }
                        phone = phone.substring(1);
                        phone = "263"+phone;

                        String format = phone.substring(0,5);
                        Log.d("WALLET",format);
                        String econet = "26377";
                        String telecel = "26373";

                        if(format.matches(econet)){
                            a.setWallet("ecocash");
                        }else if(format.matches(telecel)){
                            a.setWallet("telecash");
                        }else{
                            Log.d("WALLET",format);
                            return;
                        }
                    }else if(twoSixThree.matches("263")){
                        String format = phone.substring(0,5);
                        Log.d("WALLET",format);
                        String econet = "26377";
                        String telecel = "26373";

                        if(format.matches(econet)){
                            a.setWallet("ecocash");
                        }else if(format.matches(telecel)){
                            a.setWallet("telecash");
                        }else{
                            Log.d("WALLET",format);
                            return;
                        }
                    }else{
                        Log.d("WALLET","The phone number you entered is not on the Econet or Telecell network.");
                        return;
                    }


                    Account acc = db.where(Account.class)
                            .equalTo("phone", phone)
                            .findFirst();


                    if(acc != null){
                        Log.d("WALLET","The contact already exists within the system.");
                        return;
                    }

                    a.setPhone("+"+phone);
                    a.setName(contactName);

                    db.beginTransaction();
                    db.copyToRealmOrUpdate(a);
                    db.commitTransaction();

                    adapter.addItem(a);
                    adapter.notifyDataSetChanged();
                }
            default:
                break;
        }
    }

    //handle clicks from the different TextView on the account list - from adapter
    public void onClick(Fragment f){
        String TAG = (String)f.getArguments().get("TAG");
        AccountCreateTargetFragment f1 = null;
        AccountTargetFragment f2 = null;
        switch (TAG){
            case "CREATE_TARGET":
                RealmQuery<PaymentPlan> query = db.where(PaymentPlan.class);
                List<PaymentPlan> result = query.findAll();
                f1 = (AccountCreateTargetFragment)f;
                f1.setPaymentPlans(result);
                FragmentTransaction fragmentTransaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.frame, f1);
                fragmentTransaction1.commit();
                break;
            case "VIEW_TARGET":
                f2 = (AccountTargetFragment)f;
                FragmentTransaction fragmentTransaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.frame, f2);
                fragmentTransaction2.commit();
                break;
        }


    }

    public RecyclerView getRecyclerView(){
        return this.recyclerView;
    }

}