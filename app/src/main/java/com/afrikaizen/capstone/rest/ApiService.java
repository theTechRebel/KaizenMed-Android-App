package com.afrikaizen.capstone.rest;

import com.squareup.otto.Bus;


//import retrofit2.RetrofitError;
//import retrofit2.client.Response;


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
/*
    @Subscribe
    public void doctorsLogin(Doctor.Data doc){
        api.getDoctor(doc.getDoctorsName(), doc.getPassWord(), new Callback<Doctor.JSONObject>() {
            @Override
            public void onResponse(retrofit.Response<Doctor.JSONObject> response, Retrofit retrofit) {
                bus.post(response);
            }

            @Override
            public void onFailure(Throwable t) {
                Doctor.Error doc = new Doctor.Error(t.getMessage()+" "+t.getCause());
            }
        });
    }

    @Subscribe
    public void getResults(_PatientsResults.Data requestResults){
        api.getResults(requestResults.getWard(), requestResults.getName(), new Callback<ArrayList<_PatientsResults.JSONObject>>() {
            @Override
            public void onResponse(retrofit.Response<ArrayList<_PatientsResults.JSONObject>> response, Retrofit retrofit) {
                bus.post(response);
            }

            @Override
            public void onFailure(Throwable t) {
                _PatientsResults.Error er = new _PatientsResults.Error(t.getMessage()+" "+ t.getCause());
                bus.post(er);
            }
        });
    }
    */
}
