package com.afrikaizen.capstone.rest;

import com.afrikaizen.capstone.models.Account;
import com.afrikaizen.capstone.models.PaymentPlan;
import com.afrikaizen.capstone.models.Target;
import com.afrikaizen.capstone.models.Transaction;
import com.afrikaizen.capstone.models._PatientsResults;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Steve on 07/08/2015.
 */
public interface API {

    @FormUrlEncoded
    @POST("/integration/api/sync.php")
    Call<String> sync(@Field("transactions[]") ArrayList<Transaction> transactions,
                      @Field("accounts[]")ArrayList<Account> accounts,
                      @Field("targets[]")ArrayList<Target> targets);
}
