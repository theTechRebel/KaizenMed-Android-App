package com.afrikaizen.kaizenmed.activities;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.afrikaizen.kaizenmed.R;
import com.afrikaizen.kaizenmed.controllers.AuthFragment;
import com.afrikaizen.kaizenmed.models.NewActivity;
import com.afrikaizen.kaizenmed.orm.RealmService;
import com.afrikaizen.kaizenmed.rest.API;
import com.afrikaizen.kaizenmed.rest.ApiService;
import com.afrikaizen.kaizenmed.singleton.AppBus;
import com.afrikaizen.kaizenmed.singleton.AppPreferences;
import com.squareup.otto.Subscribe;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Retrofit;

/**
 * Created by Steve on 07/08/2015.
 */
public class AuthActivity extends AppCompatActivity {

    public static final String ENDPOINT = "http://192.168.153.1/kaizen/KaizenMed/";
    public Realm realm;

    @Override
    protected void onResume() {
        super.onResume();
        AppBus.getInstance().register(this);
        AppBus.getInstance().register(new ApiService(buildApi(), AppBus.getInstance()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppBus.getInstance().unregister(this);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.realm = RealmService.getInstance(this.getApplication()).getRealm();

        if(AppPreferences.getInstance(this).getOrganisationName() != AppPreferences.DEFAULT_VALUE_STRING){
            startNewActivity(new NewActivity(0));
        }else{
            setContentView(R.layout.activity_auth);
            swapFragments(0);
        }
    }

    @Subscribe
    public void swapFragments(int position) {
        Fragment f = new Fragment();
        switch(position){
            case 0:
                f = new AuthFragment();
                break;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.auth_frame, f);
        fragmentTransaction.commit();
    }

    @Subscribe
    public void startNewActivity(NewActivity i) {
        if (i.getActivityNumber()==0){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private API buildApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .build();

        return  retrofit.create(API.class);
    }

}
