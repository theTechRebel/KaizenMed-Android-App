package com.afrikaizen.kaizenmed.rest;

import com.afrikaizen.kaizenmed.models.Doctor;
import com.afrikaizen.kaizenmed.models.PatientsResults;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Steve on 07/08/2015.
 */
public interface API {
    @GET("/api/doctor/app/{surname}/{name}")
    void getDoctor(@Path("surname") String username, @Path("name") String name, Callback<Doctor.JSONObject> doc);

    @GET("/api/results/{ward}/{name}")
    void getResults(@Path("ward") String ward, @Path("name") String name, Callback<ArrayList<PatientsResults.JSONObject>> results);
}
