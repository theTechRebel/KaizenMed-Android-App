package com.afrikaizen.capstone.rest;

import android.util.Log;

import com.afrikaizen.capstone.models.Account;
import com.afrikaizen.capstone.models.Target;
import com.afrikaizen.capstone.models.Transaction;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.realm.internal.Context;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//import retrofit2.RetrofitError;
//import retrofit2.client.Response;


/**
 * Created by Steve on 07/08/2015.
 */
public class ApiService {
    private static ApiService API_SERVICE;
    static String URL = "http://192.168.74.1/";

    public ApiService() {}

    public static ApiService getInstance(){
        if(API_SERVICE == null){
            API_SERVICE = new ApiService();
        }
        return API_SERVICE;
    }

    public void sync(ArrayList<Transaction> transactions, ArrayList<Account> accounts, ArrayList<Target> targets){

        Retrofit myRetrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = myRetrofit.create(API.class);

        Call<String> call = api.sync(transactions,accounts,targets);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("API",response.body().toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("API",t.toString()+ " " + call.toString());
            }
        });
    }
}
