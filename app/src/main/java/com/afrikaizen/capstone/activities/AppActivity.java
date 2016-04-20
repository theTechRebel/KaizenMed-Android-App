package com.afrikaizen.capstone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.models.Account;
import com.afrikaizen.capstone.models.Target;
import com.afrikaizen.capstone.models.Transaction;
import com.afrikaizen.capstone.orm.RealmService;
import com.afrikaizen.capstone.singleton.AppBus;
import com.afrikaizen.capstone.singleton.AppPreferences;
import com.tuenti.smsradar.Sms;
import com.tuenti.smsradar.SmsListener;
import com.tuenti.smsradar.SmsRadar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import io.realm.Realm;

/**
 * Created by Steve on 22/3/2016.
 */
public class AppActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    private final String RECIPIENT_NUMBER_ECOCASH = "263777902073";
    private final String RECIPIENT_NUMBER_TELECASH = "";
    Realm db;
    SimpleDateFormat sdf;

    String activity;

    //UI elements
    private Toolbar toolbar;
    private NavigationView navigationView;
    private  DrawerLayout drawerLayout;
    Menu navigationMenu;

    //Defining Variables
    TextView organisationName;

    @Override
    protected void onResume() {
        super.onResume();
        AppBus.getInstance().register(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        AppBus.getInstance().unregister(this);
    }

    protected void setUpActivity() {
        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Find the header of the navigation view
        //RelativeLayout headerView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.header, null);



        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        this.navigationMenu = navigationView.getMenu();

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(this);
        RelativeLayout headerView = (RelativeLayout)navigationView.getHeaderView(0);

        // Initializing Drawer Layout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        //initialise the ActionBarDrawerToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        organisationName = (TextView)headerView.findViewById(R.id.organisationName);
        organisationName.setText(AppPreferences.getInstance(this).getOrganisationName());

        SmsRadar.initializeSmsRadarService(getApplication(), new SmsListener() {
            @Override
            public void onSmsSent(Sms sms) {
                //Log.d("SMS-RECIEVED","MESSAGE: "+sms.getMsg().toString()+" SENDER: "+sms.getAddress()+" DATE: "+sms.getDate());
            }

            @Override
            public void onSmsReceived(Sms sms) {
                Log.d("SMS-RECIEVED","MESSAGE: "+sms.getMsg().toString()+" SENDER: "+sms.getAddress()+" DATE: "+sms.getDate());
                String address = sms.getAddress().substring(1);

                if(address.matches(RECIPIENT_NUMBER_ECOCASH)){
                    String customer = sms.getMsg().substring(sms.getMsg().indexOf("+"), sms.getMsg().indexOf("+") + 13);
                    String dollars = sms.getMsg().substring(sms.getMsg().indexOf("USD"),sms.getMsg().length());
                    dollars = dollars.substring(3);
                    String code = sms.getMsg().substring(sms.getMsg().indexOf(":"),sms.getMsg().indexOf(":")+21);
                    code = code.substring(1);

                    Log.d("SMS-RECIEVED","SMS MATCH!! :)");

                    Log.d("SMS-RECIEVED",customer);
                    Log.d("SMS-RECIEVED",dollars);
                    Log.d("SMS-RECIEVED",code);
                    saveTransaction(customer,dollars,code);
                }else{
                    Log.d("SMS-RECIEVED",address);
                    Log.d("SMS-RECIEVED",RECIPIENT_NUMBER_ECOCASH);
                }
            }
        });

    }

    //Implementation of the Navigation View Item Selected Listener handling the item click of the navigation menu
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        Transaction t = new Transaction();

        //Closing drawer on item click
        drawerLayout.closeDrawers();
        //Check to see which item was being clicked and perform appropriate action
        if(activity.matches("transactions")){
            switch (menuItem.getItemId()) {
                case R.id.status:
                    toolbar.setTitle("Status");
                    t.setPaymentType("all");
                    AppBus.getInstance().post(t);
                    break;
                case R.id.incoming:
                    toolbar.setTitle("Incoming");
                    t.setPaymentType("Incoming Payment");
                    AppBus.getInstance().post(t);
                    break;
                case R.id.outgoing:
                    toolbar.setTitle("Outgoing");
                    t.setPaymentType("Outgoing Payment");
                    AppBus.getInstance().post(t);
                    break;
                case R.id.inventory:
                    Intent intent = new Intent(this, PaymentPlanActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.accounts:
                    Intent intent1 = new Intent(this, AccountActivity.class);
                    startActivity(intent1);
                    finish();
                    break;
                case R.id.audit:
                    break;
                case R.id.settings:
                    break;
                default:
                    break;
            }
        }else{
            switch (menuItem.getItemId()) {
                case R.id.status:
                    Intent intent1 = new Intent(this, TransactionActivity.class);
                    intent1.putExtra("item","status");
                    startActivity(intent1);
                    finish();
                    break;
                case R.id.incoming:
                    Intent intent2 = new Intent(this, TransactionActivity.class);
                    intent2.putExtra("item","incoming");
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.outgoing:
                    Intent intent3 = new Intent(this, TransactionActivity.class);
                    intent3.putExtra("item","outgoing");
                    startActivity(intent3);
                    finish();
                    break;
                case R.id.inventory:
                    Intent intent4 = new Intent(this, PaymentPlanActivity.class);
                    startActivity(intent4);
                    finish();
                    break;
                case R.id.accounts:
                    Intent intent5 = new Intent(this, AccountActivity.class);
                    startActivity(intent5);
                    finish();
                    break;
                case R.id.audit:
                    break;
                case R.id.settings:
                    break;
                default:
                    break;
            }
        }

        //Checking if the item is in checked state or not, if not make it in checked state
        if(menuItem.isChecked()) menuItem.setChecked(false);
        else menuItem.setChecked(true);
        return true;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    private void saveTransaction(String customer,String dollars, String code){

        Log.d("SMS-RECIEVED","Method called :)");

        sdf = new SimpleDateFormat("yyyy MMM dd");

        db = RealmService.getInstance(getApplication()).getRealm();
        db.beginTransaction();

        Account a = db.where(Account.class)
                .equalTo("phone",customer)
                .equalTo("targets.active",true)
                .findFirst();

        String phone = customer;
        String details = "";
        String confirmationCode = code;
        Double amount = Double.parseDouble(dollars);

        Target targets = a.getTargets().first();

        Transaction t = new Transaction();
        t.setPaymentType("Incoming Payment");
        try {
            Date date = new Date();
            t.setDate(sdf.parse(sdf.format(date)));
        } catch (ParseException e) {
            Log.d("DATE-ERROR",e.toString());
        }

        int id = 0;
        try{
            id = (int) (db.where(Transaction.class).max("id").intValue() + 1);
        }catch(NullPointerException ex){
            Log.d("REALM_ERROR",ex.toString());
            id = 1;
        }

        t.setId(id);
        t.setAmount(amount);
        t.setConfirmaionCode(confirmationCode);
        t.setCustomerDetails(a.getName());
        t.setDetails(a.getTargets().get(0).getPlan().getPackageName());
        t.setWallet(a.getWallet());

        a.getTransactions().add(t);

        Log.d("REALM-OBJECT",t.getId()+" "+t.getAmount()+" "+t.getConfirmaionCode()+" "+t.getCustomerDetails()+" "+t.getDetails()+" "+t.getWallet()+" "+t.getDate().toString()+" "+t.getPaymentType());
        Log.d("REALM-OBJECT",""+a.getTransactions().size());


        db.copyToRealmOrUpdate(t);
        db.copyToRealmOrUpdate(a);
        db.commitTransaction();

        ArrayList<Transaction> data =
                new ArrayList<Transaction>(Arrays.<Transaction>asList());
        data.add(t);

        AppBus.getInstance().post(data);

    }
}
