package com.afrikaizen.capstone.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Steve on 22/3/2016.
 */
public class AppActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    private final String RECIPIENT_NUMBER_ECOCASH = "+263775013145";
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
        SmsRadar.initializeSmsRadarService(getApplicationContext(), new SmsListener() {
            @Override
            public void onSmsSent(Sms sms) {}

            @Override
            public void onSmsReceived(Sms sms) {saveTransaction(sms);}
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        AppBus.getInstance().unregister(this);
    }

    protected void setUpActivity() {
        // Initializing Toolbar and setting it as the actionbar
        db = RealmService.getInstance(getApplication()).getRealm();
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
                    Intent intent = new Intent(this, PaymentPlansActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.accounts:
                    Intent intent1 = new Intent(this, AccountsActivity.class);
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
                    Intent intent1 = new Intent(this, TransactionsActivity.class);
                    intent1.putExtra("item","status");
                    startActivity(intent1);
                    finish();
                    break;
                case R.id.incoming:
                    Intent intent2 = new Intent(this, TransactionsActivity.class);
                    intent2.putExtra("item","incoming");
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.outgoing:
                    Intent intent3 = new Intent(this, TransactionsActivity.class);
                    intent3.putExtra("item","outgoing");
                    startActivity(intent3);
                    finish();
                    break;
                case R.id.inventory:
                    Intent intent4 = new Intent(this, PaymentPlansActivity.class);
                    startActivity(intent4);
                    finish();
                    break;
                case R.id.accounts:
                    Intent intent5 = new Intent(this, AccountsActivity.class);
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


    private void saveTransaction(Sms sms){
        sdf = new SimpleDateFormat("yyyy MMM dd");
        String phone = "";
        String details = "";
        String confirmationCode = "";
        Double amount = 0.0;

        Account a = db.where(Account.class)
                .equalTo("phone",phone)
                .equalTo("targets.active",true)
                .findFirst();

        int iNumber = sms.getMsg().indexOf("+");
        phone = sms.getMsg().substring(iNumber,iNumber+12);

        int iCode = sms.getMsg().indexOf("code");
        iCode +=4;
        confirmationCode = sms.getMsg().substring(iCode,iCode+17);

        switch(sms.getAddress()){
            case RECIPIENT_NUMBER_ECOCASH:
                Transaction t = new Transaction();
                t.setPaymentType(sms.getType().name());
                try {
                    t.setDate(sdf.parse(sms.getDate()));
                } catch (ParseException e) {
                    Log.d("DATE-ERROR",e.toString());
                }

                t.setAmount(amount);
                t.setConfirmaionCode(confirmationCode);
                t.setCustomerDetails(a.getName());
                t.setDetails(a.getTargets().get(0).getPlan().getPackageName());
                break;
            case RECIPIENT_NUMBER_TELECASH:
                break;
        }
    }
}
