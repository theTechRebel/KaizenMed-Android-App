package com.afrikaizen.kaizenmed.singleton;

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
    private String doctorsName = "doctorsName";
    private String id = "id";
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

    public String getDoctorsName(){
        return sharedPreferences.getString(doctorsName,DEFAULT_VALUE_STRING);
    }

    public void setDoctorsName(String doctorsName){
        editor.putString(this.doctorsName, doctorsName).commit();
    }

    public String getDoctorsID(){
        return sharedPreferences.getString(id,DEFAULT_VALUE_STRING);
    }

    public void setDoctorsID(String id){
        editor.putString(this.id, id).commit();
    }

    public String getDataPersisted() {
        return sharedPreferences.getString(dataPersisted,DEFAULT_VALUE_STRING);
    }

    public void setDataPersisted(String dataPersisted) {
        editor.putString(this.dataPersisted, dataPersisted).commit();
    }
}
