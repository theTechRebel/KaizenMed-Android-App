package com.afrikaizen.capstone.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import com.afrikaizen.capstone.activities.contacts.ui.ContactsListActivity;
import com.afrikaizen.capstone.controllers.AccountCreateTargetFragment;
import com.afrikaizen.capstone.controllers.AccountsFragment;
import com.afrikaizen.capstone.controllers.PaymentPlansFragment;
import com.afrikaizen.capstone.imports.SampleCalendarDateBasicUsage;
import com.afrikaizen.capstone.models.Account;
import com.afrikaizen.capstone.models.NewActivity;
import com.afrikaizen.capstone.models.PaymentPlan;
import com.afrikaizen.capstone.orm.RealmService;
import com.afrikaizen.capstone.singleton.AppBus;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.codinguser.android.contactpicker.ContactsPickerActivity;
import com.squareup.otto.Subscribe;

import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;

import com.afrikaizen.capstone.R;

/**
 * Created by Steve on 31/3/2016.
 */
public class AccountsActivity extends AppActivity  {

    private DrawerLayout drawerLayout;
    Realm db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.db = RealmService.getInstance(getApplication()).getRealm();
        this.activity = "accounts";
        setContentView(R.layout.activity_main);
        setUpActivity();

        AccountsFragment f = new AccountsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, f);
        fragmentTransaction.commit();

    }
}
