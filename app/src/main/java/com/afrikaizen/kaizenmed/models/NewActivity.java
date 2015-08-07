package com.afrikaizen.kaizenmed.models;

/**
 * Created by Steve on 07/08/2015.
 */
public class NewActivity {
    private int activityNumber;
    public NewActivity(int activityNumber){
        this.activityNumber = activityNumber;
    }

    public int getActivityNumber() {
        return activityNumber;
    }

    public void setActivityNumber(int activityNumber) {
        this.activityNumber = activityNumber;
    }
}
