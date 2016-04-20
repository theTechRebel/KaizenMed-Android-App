package com.afrikaizen.capstone.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;

import com.afrikaizen.capstone.controllers.AccountFragment;
import com.afrikaizen.capstone.orm.RealmService;

import io.realm.Realm;

import com.afrikaizen.capstone.R;

/**
 * Created by Steve on 31/3/2016.
 */
public class AccountActivity extends AppActivity {

    private DrawerLayout drawerLayout;
    Realm db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.db = RealmService.getInstance(getApplication()).getRealm();
        this.activity = "accounts";
        setContentView(R.layout.activity_main);
        setUpActivity();

        //onNavigationItemSelected(navigationMenu.findItem(R.id.accounts));
        AccountFragment f = new AccountFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, f);
        fragmentTransaction.commit();

    }
}
