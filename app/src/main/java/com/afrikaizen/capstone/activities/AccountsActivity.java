package com.afrikaizen.capstone.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
public class AccountsActivity extends AppActivity implements CalendarDatePickerDialogFragment.OnDateSetListener {
    private static final int GET_PHONE_NUMBER = 3007;
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_accounts";
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

    @Subscribe
    public void onAccountSelected(Account a) {
        RealmQuery<PaymentPlan> query = db.where(PaymentPlan.class);
        List<PaymentPlan> result = query.findAll();

        AccountCreateTargetFragment f = new AccountCreateTargetFragment();
        f.setAccount(a);
        f.setPaymentPlans(result);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, f);
        fragmentTransaction.commit();
    }

    @Subscribe
    public void startNewActivity(NewActivity i) {
        if (i.getActivityNumber() == 0) {
            startActivityForResult(new Intent(this, ContactsPickerActivity.class), GET_PHONE_NUMBER);

            //native android method of contact finding - still havent figured it out yet :(
            //startActivity(new Intent(this, ContactsListActivity.class));
        } else if (i.getActivityNumber() == 1) {
            CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                    .setOnDateSetListener(this);
            cdp.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
        }
    }

    // Listen for results.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // See which child activity is calling us back.
        switch (requestCode) {
            case GET_PHONE_NUMBER:
                // This is the standard resultCode that is sent back if the
                // activity crashed or didn't doesn't supply an explicit result.
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "No contact selected.", Toast.LENGTH_LONG).show();
                } else {
                    String phoneNumber = (String) data.getExtras().get(ContactsPickerActivity.KEY_PHONE_NUMBER);
                    String contactName = (String) data.getExtras().get(ContactsPickerActivity.KEY_CONTACT_NAME);

                    Account a = new Account();
                    a.setPhone(phoneNumber);
                    a.setName(contactName);

                    AppBus.getInstance().post(a);
                }
            default:
                break;
        }
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        String date = (getString(R.string.calendar_date_picker_result_values, year, monthOfYear, dayOfMonth));
        Toast.makeText(this, date, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        // Example of reattaching to the fragment
        super.onResume();
        CalendarDatePickerDialogFragment calendarDatePickerDialogFragment = (CalendarDatePickerDialogFragment) getSupportFragmentManager()
                .findFragmentByTag(FRAG_TAG_DATE_PICKER);
        if (calendarDatePickerDialogFragment != null) {
            calendarDatePickerDialogFragment.setOnDateSetListener(this);
        }
    }

}
