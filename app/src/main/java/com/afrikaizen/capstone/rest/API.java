package com.afrikaizen.capstone.rest;

import com.afrikaizen.capstone.models.Account;
import com.afrikaizen.capstone.models.PaymentPlan;
import com.afrikaizen.capstone.models.Target;
import com.afrikaizen.capstone.models.Transaction;
import com.afrikaizen.capstone.models._PatientsResults;

import java.util.ArrayList;
import java.util.Date;

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

    @FormUrlEncoded
    @POST("/integration/api/account.php")
    Call<String> account(@Field("id") int id,
                      @Field("name")String name,
                      @Field("surname")String surname,
                      @Field("phone")String phone,
                      @Field("email")String email,
                      @Field("idnumber")String idnumber,
                      @Field("additionalInformation")String additionalInfo,
                      @Field("wallet")String wallet,
                      @Field("accountNumber")String accountNumber);

    @FormUrlEncoded
    @POST("/integration/api/invoices.php")
    Call<String> invoices(@Field("paymentType") String paymentType,
                         @Field("customer") String customer,
                         @Field("quantity") int quantity,
                         @Field("amount") Double amount,
                         @Field("dateCreated") Date dateCreated,
                         @Field("startDate") Date startDate,
                         @Field("description") String description,
                         @Field("endDate") Date endDate);

    @FormUrlEncoded
    @POST("/integration/api/payment.php")
    Call<String> payments(@Field("fullname") String fullName,
                         @Field("txndate") Date txndate,
                         @Field("refnumber") String refnumber,
                         @Field("confirmationcode") String confirmationcode,
                         @Field("totalamount") Double amount);

    @FormUrlEncoded
    @POST("/integration/api/stats.php")
    Call<String> stats(@Field("ecocash")  Double ecocash,
                          @Field("telecash") Double telecash,
                          @Field("customers") int customers,
                          @Field("plans") int plans,
                          @Field("trasactions") int transactions);
}