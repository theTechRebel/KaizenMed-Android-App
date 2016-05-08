package com.afrikaizen.capstone.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.controllers.SettingsFragment;
import com.afrikaizen.capstone.singleton.AppPreferences;

/**
 * Created by Steve on 8/5/2016.
 */
public class SettingsActivity extends AppActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpActivity();

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(R.id.frame, new SettingsFragment())
                .commit();
    }
}
