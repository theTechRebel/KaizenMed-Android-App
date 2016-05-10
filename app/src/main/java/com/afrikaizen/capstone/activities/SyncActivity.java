package com.afrikaizen.capstone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.controllers.AuditTrailFragment;
import com.afrikaizen.capstone.services.SyncService;

/**
 * Created by Steve on 10/5/2016.
 */
public class SyncActivity extends AppActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = "sync";
        setUpActivity();
        startService();
    }

    //inference mean, mode, median
    public void startService(){
        Intent i = new Intent(this, SyncService.class);
        startService(i);
        Toast.makeText(this, "Sync started...", Toast.LENGTH_LONG).show();
    }

    public void stopService(){
        Intent i = new Intent(this, SyncService.class);
        stopService(i);
        Toast.makeText(this, "Sync stopped...", Toast.LENGTH_LONG).show();
    }
}
