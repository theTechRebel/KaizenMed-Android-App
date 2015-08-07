package com.afrikaizen.kaizenmed.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.afrikaizen.kaizenmed.R;
import com.afrikaizen.kaizenmed.controllers.AuthFragment;
import com.afrikaizen.kaizenmed.models.NewActivity;
import com.afrikaizen.kaizenmed.rest.API;
import com.afrikaizen.kaizenmed.rest.ApiService;
import com.afrikaizen.kaizenmed.singleton.AppBus;
import com.squareup.otto.Subscribe;

import retrofit.RestAdapter;

/**
 * Created by Steve on 07/08/2015.
 */
public class AuthActivity extends AppCompatActivity {

    public static final String ENDPOINT = "http://www.afrikaizen.com/kaizenmed";

    @Override
    protected void onStart() {
        super.onStart();
        AppBus.getInstance().register(this);
        AppBus.getInstance().register(new ApiService(buildApi(), AppBus.getInstance()));
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppBus.getInstance().unregister(this);
        //AppBus.getInstance().unregister(new ApiService(buildApi(),AppBus.getInstance()));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        swapFragments(0);
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
        }
    }

    private API buildApi(){
        return new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(API.class);
    }

}
