package com.afrikaizen.kaizenmed.rest;

import android.util.Log;

import com.afrikaizen.kaizenmed.models.Doctor;
import com.afrikaizen.kaizenmed.models.PatientsResults;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Steve on 07/08/2015.
 */
public class ApiService {
    private API api;
    private Bus bus;

    public ApiService(API api, Bus bus) {
        this.api = api;
        this.bus = bus;
    }

    @Subscribe
    public void doctorsLogin(Doctor.Data doc){
        api.getDoctor(doc.getDoctorsName(), doc.getPassWord(), new Callback<Doctor.JSONObject>() {
            @Override
            public void success(Doctor.JSONObject doc, Response response) {
                bus.post(doc);
            }

            @Override
            public void failure(RetrofitError e) {
                Doctor.Error doc = new Doctor.Error(e.getMessage()+" "+e.getCause());
                bus.post(doc);
            }
        });
    }

    @Subscribe
    public void getResults(PatientsResults.Data requestResults){
        api.getResults(requestResults.getWard(), requestResults.getName(), new Callback<ArrayList<PatientsResults.JSONObject>>() {
            @Override
            public void success(ArrayList<PatientsResults.JSONObject> results, Response response) {
                bus.post(results);
            }

            @Override
            public void failure(RetrofitError error) {
                PatientsResults.Error er = new PatientsResults.Error(error.getMessage()+" "+ error.getCause());
                bus.post(er);
            }
        });
    }
}
