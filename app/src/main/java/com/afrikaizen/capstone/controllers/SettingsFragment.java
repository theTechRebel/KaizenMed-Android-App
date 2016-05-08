package com.afrikaizen.capstone.controllers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.EditText;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.singleton.AppPreferences;

/**
 * Created by Steve on 8/5/2016.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {


    private Preference pref;
    private String summaryStr;
    String prefixStr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences sharedPref = getPreferenceScreen().getSharedPreferences();
        sharedPref.registerOnSharedPreferenceChangeListener(this);

        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
        EditTextPreference org = (EditTextPreference) findPreference("orgName");
        org.setSummary(sp.getString("orgName", AppPreferences.getInstance(getActivity()).getOrganisationName()));
        EditTextPreference ecocash = (EditTextPreference)findPreference("ecocash");
        ecocash.setSummary(sp.getString("ecocash", AppPreferences.getInstance(getActivity()).getEcoCashWallet()));
        EditTextPreference telecash = (EditTextPreference)findPreference("telecash");
        telecash.setSummary(sp.getString("telecash", AppPreferences.getInstance(getActivity()).getTeleCashWallet()));
        CheckBoxPreference syncTransactions = (CheckBoxPreference)findPreference("syncTransactions");
        syncTransactions.setChecked(AppPreferences.getInstance(getActivity()).getSyncTransactions());
        EditTextPreference syncToURL = (EditTextPreference)findPreference("syncToURL");
        syncToURL.setSummary(sp.getString("syncToURL", AppPreferences.getInstance(getActivity()).getSyncToURL()));
        EditTextPreference syncFromURL = (EditTextPreference)findPreference("syncFromURL");
        syncFromURL.setSummary(sp.getString("syncFromURL", AppPreferences.getInstance(getActivity()).getSyncFromURL()));
        ListPreference syncfrequency = (ListPreference)findPreference("syncfrequency");
        syncfrequency.setValueIndex(AppPreferences.getInstance(getActivity()).getSyncFrequency());
        EditTextPreference costA = (EditTextPreference)findPreference("costA");
        costA.setSummary(sp.getString("costA", AppPreferences.getInstance(getActivity()).getCostA().toString()));
        EditTextPreference costB = (EditTextPreference)findPreference("costB");
        costB.setSummary(sp.getString("costB",AppPreferences.getInstance(getActivity()).getCostB().toString()));
        EditTextPreference costC = (EditTextPreference)findPreference("costC");
        costC.setSummary(sp.getString("costC", AppPreferences.getInstance(getActivity()).getCostC().toString()));
        EditTextPreference costD = (EditTextPreference)findPreference("costD");
        costD.setSummary(sp.getString("costD", AppPreferences.getInstance(getActivity()).getCostD().toString()));
        EditTextPreference costE = (EditTextPreference)findPreference("costE");
        costE.setSummary(sp.getString("costE", AppPreferences.getInstance(getActivity()).getCostE().toString()));
        EditTextPreference costF = (EditTextPreference)findPreference("costF");
        costF.setSummary(sp.getString("costF", AppPreferences.getInstance(getActivity()).getCostF().toString()));
        EditTextPreference costG = (EditTextPreference)findPreference("costG");
        costG.setSummary(sp.getString("costG", AppPreferences.getInstance(getActivity()).getCostG().toString()));
        EditTextPreference costH = (EditTextPreference)findPreference("costH");
        costH.setSummary(sp.getString("costH", AppPreferences.getInstance(getActivity()).getCostH().toString()));
        EditTextPreference costI = (EditTextPreference)findPreference("costI");
        costI.setSummary(sp.getString("costI", AppPreferences.getInstance(getActivity()).getCostI().toString()));
        EditTextPreference costJ = (EditTextPreference)findPreference("costJ");
        costJ.setSummary(sp.getString("costJ", AppPreferences.getInstance(getActivity()).getCostJ().toString()));
        EditTextPreference costK = (EditTextPreference)findPreference("costK");
        costK.setSummary(sp.getString("costK", AppPreferences.getInstance(getActivity()).getCostK().toString()));
        EditTextPreference costL = (EditTextPreference)findPreference("costL");
        costL.setSummary(sp.getString("costL", AppPreferences.getInstance(getActivity()).getCostL().toString()));
        EditTextPreference costM = (EditTextPreference)findPreference("costM");
        costM.setSummary(sp.getString("costM", AppPreferences.getInstance(getActivity()).getCostM().toString()));
        EditTextPreference costN = (EditTextPreference)findPreference("costN");
        costN.setSummary(sp.getString("costN", AppPreferences.getInstance(getActivity()).getCostN().toString()));
        EditTextPreference costO = (EditTextPreference)findPreference("costO");
        costO.setSummary(sp.getString("costO", AppPreferences.getInstance(getActivity()).getCostO().toString()));
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Preference pref = findPreference(key);
        if (pref instanceof EditTextPreference) {
            summaryStr = (String) pref.getSummary();

            if (key == "orgName" | key == "ecocash" | key == "telecash" | key == "syncToURL" | key == "syncFromURL") {
                //Get the user input data
                prefixStr = sharedPreferences.getString(key, "");
            } else {
                //Get the user input data
                prefixStr = sharedPreferences.getString(key, "0.00");
            }


            //Update the summary with user input data
            pref.setSummary(prefixStr);
        }
    }
}
