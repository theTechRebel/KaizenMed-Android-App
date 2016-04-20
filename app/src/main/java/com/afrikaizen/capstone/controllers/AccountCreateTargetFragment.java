package com.afrikaizen.capstone.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.models.Account;
import com.afrikaizen.capstone.models.ExpectedPayments;
import com.afrikaizen.capstone.models.PaymentPlan;
import com.afrikaizen.capstone.models.Target;
import com.afrikaizen.capstone.orm.RealmService;
import com.afrikaizen.capstone.singleton.AppBus;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Steve on 12/4/2016.
 */
public class AccountCreateTargetFragment extends Fragment implements View.OnClickListener,
        CalendarDatePickerDialogFragment.OnDateSetListener, AdapterView.OnItemSelectedListener, View.OnFocusChangeListener {

    Account account;
    PaymentPlan plan;

    Button startDate,create_target_save;
    EditText quantity;
    BetterSpinner selectServicePlan;
    TextView customerName, customerNumber,start,end,create_target_total;
    CheckBox cbRequiring;

    ArrayList<PaymentPlan> data =
            new ArrayList<PaymentPlan>(Arrays.<PaymentPlan>asList());
    String[] paymentDataArray;

    private static final String FRAG_TAG_DATE_PICKER = "AccountCreateTargetFragment";
    SimpleDateFormat sdf;
    Date d;
    int position;
    Realm db;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppBus.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account_create_target,container,false);

        db = RealmService.getInstance(getActivity().getApplication()).getRealm();

        sdf = new SimpleDateFormat("yyyy MMM dd");
        d = new Date();

        startDate = (Button)rootView.findViewById(R.id.create_target_start_date);
        create_target_save = (Button)rootView.findViewById(R.id.create_target_save);
        quantity = (EditText)rootView.findViewById(R.id.create_target_quantity);
        selectServicePlan = (BetterSpinner) rootView.findViewById(R.id.create_target_select_service_plan);
        customerName = (TextView)rootView.findViewById(R.id.create_target_customer_name);
        customerNumber = (TextView)rootView.findViewById(R.id.create_target_customer_number);
        create_target_total = (TextView)rootView.findViewById(R.id.create_target_total);
        create_target_total.setText("0");
        cbRequiring = (CheckBox)rootView.findViewById(R.id.create_target_set_recuring);
        quantity.setText("1");
        start = (TextView)rootView.findViewById(R.id.create_target_start_date_text);
        end = (TextView)rootView.findViewById(R.id.create_target_end_date_text);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity()
                .getApplicationContext(),R.layout.spinner_item);

        paymentDataArray = new String[data.size()];
        int i = 0;
        for (PaymentPlan p : data) {
            paymentDataArray[i] = p.getPackageName()+" "+p.getAmount();
            adapter.add(p.getPackageName()+" "+p.getAmount());
            i++;
        }
        customerName.setText(account.getName());
        customerNumber.setText(account.getPhone());
        selectServicePlan.setAdapter(adapter);
        selectServicePlan.setOnItemSelectedListener(this);
        selectServicePlan.setOnFocusChangeListener(this);
        quantity.setOnFocusChangeListener(this);
        startDate.setOnClickListener(this);
        create_target_save.setOnClickListener(this);
        return rootView;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setPaymentPlans(List<PaymentPlan> paymentPlans) {
        this.data.addAll(paymentPlans);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_target_start_date:
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(this);
                cdp.show(getActivity().getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
                break;
            case R.id.create_target_save:
                if(start.getText().toString().matches("") ||
                        end.getText().toString().matches("") ||
                        quantity.getText().toString().matches("")){
                    Toast.makeText(getActivity(),
                            "Please select a start date or specify the payment quantity",
                            Toast.LENGTH_LONG).show();
                }else{
                    Target t =new Target();

                    int id = 0;
                    try{
                        id = (int) (db.where(Target.class).max("id").intValue() + 1);
                    }catch(NullPointerException ex){
                        Log.d("REALM_ERROR",ex.toString());
                        id = 1;
                    }

                    Calendar c = Calendar.getInstance();

                    db.beginTransaction();
                    Account a = db.where(Account.class)
                            .equalTo("phone", account.getPhone())
                            .findFirst();

                    t.setId(id);
                    t.setPlan(plan);
                    t.setQuantity(Integer.parseInt(quantity.getText().toString()));
                    t.setDateCreated(c.getTime());
                    t.setActive(true);
                    if(cbRequiring.isChecked()){
                        t.setRequiring(true);
                    }else{
                        t.setRequiring(false);
                    }
                    t.setPaymentType("Incoming Payment");
                    try{
                        t.setStartDate(sdf.parse(start.getText().toString()));
                        t.setEndDate(sdf.parse(end.getText().toString()));
                    }catch(ParseException ex){
                        Log.d("DATE_EXCEPTION",ex.toString());
                    }
                    a.getTargets().add(t);


                    try{
                        id = (int) (db.where(ExpectedPayments.class).max("id").intValue());
                    }catch(NullPointerException ex){
                        Log.d("REALM_ERROR",ex.toString());
                        id = 1;
                    }

                    ExpectedPayments e = db.where(ExpectedPayments.class)
                            .equalTo("id",id)
                            .findFirst();
                    if(e == null){
                        e = new ExpectedPayments();
                        e.setI(id);
                        RealmList<Account> realmList = new RealmList<Account>();
                        realmList.add(a);
                        e.setAccounts(realmList);
                    }else{
                        e.getAccounts().add(a);
                    }

                    db.copyToRealmOrUpdate(e);
                    db.copyToRealmOrUpdate(t);
                    db.copyToRealmOrUpdate(a);
                    db.commitTransaction();

                    AccountTargetFragment f = new AccountTargetFragment();
                    f.setAccount(a);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame, f);
                    fragmentTransaction.commit();
                }
                break;
        }
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {

        Calendar c = new GregorianCalendar(year,monthOfYear,dayOfMonth);
        String date1 = sdf.format(c.getTime());
        start.setText(date1);

        int pos = 0;

        for (int i =0; i<paymentDataArray.length;i++){
            if(paymentDataArray[i].matches(selectServicePlan.getText().toString())){
                pos = i;
            }
        }

        plan = data.get(pos);
        c.add(Calendar.DAY_OF_MONTH, plan.getPeriod());
        end.setText(sdf.format(c.getTime()));
        Double q = Double.parseDouble(quantity.getText().toString());
        Double total = q*plan.getAmount();
        create_target_total.setText(total.toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        CalendarDatePickerDialogFragment calendarDatePickerDialogFragment = (CalendarDatePickerDialogFragment)
                getActivity().getSupportFragmentManager()
                .findFragmentByTag(FRAG_TAG_DATE_PICKER);
        if (calendarDatePickerDialogFragment != null) {
            calendarDatePickerDialogFragment.setOnDateSetListener(this);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        start.setText("");
        end.setText("");
        setTotalAmount();
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        setTotalAmount();
    }

    private void setTotalAmount(){
        if(!(selectServicePlan.getText().toString().matches(""))){
            int pos = 0;

            for (int i =0; i<paymentDataArray.length;i++){
                if(paymentDataArray[i].matches(selectServicePlan.getText().toString())){
                    pos = i;
                }
            }

            plan = data.get(pos);
            Double q = Double.parseDouble(quantity.getText().toString());
            Double total = q*plan.getAmount();
            create_target_total.setText(total.toString());
        }
    }
}
