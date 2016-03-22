package com.afrikaizen.capstone.singleton;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Steve on 07/08/2015.
 */
public class AppPreferences {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static AppPreferences PREFERENCE_INSTANCE;

    public static final String DEFAULT_VALUE_STRING = "";
    private static final String APP_PREFERENCES = "APP_PREFERENCES";

    private String orgName = "organisation name";
    private String ecocash = "ecocash";
    private String telecash = "telecash";
    private String dataPersisted = "realm";



    private AppPreferences(Context ctx) {
        this.sharedPreferences = ctx.getSharedPreferences(APP_PREFERENCES,ctx.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public static AppPreferences getInstance(Context ctx){

        if(PREFERENCE_INSTANCE == null){
            PREFERENCE_INSTANCE = new AppPreferences(ctx);
        }
        return PREFERENCE_INSTANCE;

    }

    public void setOrganisationName(String orgName){
        editor.putString(this.orgName, orgName).commit();
    }

    public String getOrganisationName(){
        return sharedPreferences.getString(orgName,DEFAULT_VALUE_STRING);
    }

    public String getEcoCashWallet(){
        return sharedPreferences.getString(ecocash,DEFAULT_VALUE_STRING);
    }

    public void setEcoCashWallet(String ecocash){
        editor.putString(this.ecocash, ecocash).commit();
    }

    public String getTeleCashWallet(){
        return sharedPreferences.getString(telecash,DEFAULT_VALUE_STRING);
    }

    public void setTeleCashWallet(String telecash){
        editor.putString(this.telecash, telecash).commit();
    }

    public Boolean getDataPersisted() {
        return sharedPreferences.getBoolean(this.dataPersisted,false);
    }

    public void setDataPersisted(Boolean dataPersisted) {
        editor.putBoolean(this.dataPersisted, dataPersisted).commit();
    }
}
