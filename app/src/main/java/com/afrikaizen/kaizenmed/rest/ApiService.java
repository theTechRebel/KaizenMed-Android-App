package com.afrikaizen.kaizenmed.rest;

import com.afrikaizen.kaizenmed.models.Doctor;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

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
}
