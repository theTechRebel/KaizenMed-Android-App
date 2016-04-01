package com.afrikaizen.capstone.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.models.Transaction;
import com.afrikaizen.capstone.singleton.AppBus;
import com.afrikaizen.capstone.singleton.AppPreferences;

/**
 * Created by Steve on 22/3/2016.
 */
public class AppActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

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

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        this.navigationMenu = navigationView.getMenu();

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(this);

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

        organisationName = (TextView) findViewById(R.id.organisationName);

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



}
