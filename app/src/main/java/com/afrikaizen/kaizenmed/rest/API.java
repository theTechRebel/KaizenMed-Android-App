package com.afrikaizen.kaizenmed.rest;

import com.afrikaizen.kaizenmed.models.Doctor;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Steve on 07/08/2015.
 */
public interface API {
    @GET("/auth/doctor/app/{surname}/{name}")
    void getDoctor(@Path("surname") String username, @Path("name") String name, Callback<Doctor.JSONObject> doc);
}
