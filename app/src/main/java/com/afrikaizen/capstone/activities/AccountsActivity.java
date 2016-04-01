package com.afrikaizen.capstone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.controllers.AccountsFragment;
import com.afrikaizen.capstone.controllers.PaymentPlansFragment;
import com.afrikaizen.capstone.models.NewActivity;
import com.codinguser.android.contactpicker.ContactsPickerActivity;
import com.squareup.otto.Subscribe;

/**
 * Created by Steve on 31/3/2016.
 */
public class AccountsActivity extends AppActivity{
    private static final int GET_PHONE_NUMBER = 3007;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = "accounts";
        setContentView(R.layout.activity_main);
        setUpActivity();

        AccountsFragment f = new AccountsFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, f);
        fragmentTransaction.commit();

        //onNavigationItemSelected(navigationMenu.findItem(R.id.accounts));
    }

    @Subscribe
    public void startNewActivity(NewActivity i) {
        if (i.getActivityNumber()==0){
            startActivityForResult(new Intent(this, ContactsPickerActivity.class), GET_PHONE_NUMBER);

        }
    }

    // Listen for results.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        // See which child activity is calling us back.
        switch (requestCode) {
            case GET_PHONE_NUMBER:
                // This is the standard resultCode that is sent back if the
                // activity crashed or didn't doesn't supply an explicit result.
                if (resultCode == RESULT_CANCELED){
                    Toast.makeText(this, "No contact selected.", Toast.LENGTH_LONG).show();
                }
                else {
                    String phoneNumber = (String) data.getExtras().get(ContactsPickerActivity.KEY_PHONE_NUMBER);
                    //TODO: Get the contact details and save them in db as a new account
                    Toast.makeText(this, "Phone number found: " + phoneNumber , Toast.LENGTH_LONG).show();
                }
            default:
                break;
        }
    }
}
