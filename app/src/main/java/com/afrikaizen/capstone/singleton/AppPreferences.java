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
    private Boolean syncTransactions = true;
    private Boolean loggedIn = false;
    private int syncFrequency = 0;
    private String syncToURL = "http://localhost/integration/app/sync.php";
    private String syncFromURL = "http://localhost/integration/app/get.php";
    Double costA = 0.14;
    Double costB = 0.14;
    Double costC = 0.24;
    Double costD = 0.44;
    Double costE = 0.62;
    Double costF = 0.82;
    Double costG = 1.03;
    Double costH = 1.45;
    Double costI = 1.52;
    Double costJ = 1.98;
    Double costK = 2.48;
    Double costL = 2.98;
    Double costM = 3.79;
    Double costN = 3.95;
    Double costO = 3.98;



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

    public Boolean getSyncTransactions() {
        return syncTransactions;
    }

    public void setSyncTransactions(Boolean syncTransactions) {
        this.syncTransactions = syncTransactions;
    }

    public String getSyncToURL() {
        return syncToURL;
    }

    public void setSyncToURL(String syncToURL) {
        this.syncToURL = syncToURL;
    }

    public String getSyncFromURL() {
        return syncFromURL;
    }

    public void setSyncFromURL(String syncFromURL) {
        this.syncFromURL = syncFromURL;
    }

    public Double getCostA() {
        return costA;
    }

    public void setCostA(Double costA) {
        this.costA = costA;
    }

    public Double getCostB() {
        return costB;
    }

    public void setCostB(Double costB) {
        this.costB = costB;
    }

    public Double getCostC() {
        return costC;
    }

    public void setCostC(Double costC) {
        this.costC = costC;
    }

    public Double getCostD() {
        return costD;
    }

    public void setCostD(Double costD) {
        this.costD = costD;
    }

    public Double getCostE() {
        return costE;
    }

    public void setCostE(Double costE) {
        this.costE = costE;
    }

    public Double getCostF() {
        return costF;
    }

    public void setCostF(Double costF) {
        this.costF = costF;
    }

    public Double getCostG() {
        return costG;
    }

    public void setCostG(Double costG) {
        this.costG = costG;
    }

    public Double getCostH() {
        return costH;
    }

    public void setCostH(Double costH) {
        this.costH = costH;
    }

    public Double getCostI() {
        return costI;
    }

    public void setCostI(Double costI) {
        this.costI = costI;
    }

    public Double getCostJ() {
        return costJ;
    }

    public void setCostJ(Double costJ) {
        this.costJ = costJ;
    }

    public Double getCostK() {
        return costK;
    }

    public void setCostK(Double costK) {
        this.costK = costK;
    }

    public Double getCostL() {
        return costL;
    }

    public void setCostL(Double costL) {
        this.costL = costL;
    }

    public Double getCostM() {
        return costM;
    }

    public void setCostM(Double costM) {
        this.costM = costM;
    }

    public Double getCostN() {
        return costN;
    }

    public void setCostN(Double costN) {
        this.costN = costN;
    }

    public Double getCostO() {
        return costO;
    }

    public void setCostO(Double costO) {
        this.costO = costO;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public int getSyncFrequency() {
        return syncFrequency;
    }

    public void setSyncFrequency(int syncFrequency) {
        this.syncFrequency = syncFrequency;
    }
}
