package com.afrikaizen.capstone.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afrikaizen.capstone.R;
import com.afrikaizen.capstone.controllers.TransactionsFragment;
import com.afrikaizen.capstone.imports.SlidingTabLayout;
import com.afrikaizen.capstone.models.PatientsResults;
import com.afrikaizen.capstone.models.Transaction;
import com.afrikaizen.capstone.rest.API;
import com.afrikaizen.capstone.rest.ApiService;
import com.afrikaizen.capstone.singleton.AppBus;
import com.afrikaizen.capstone.singleton.AppPreferences;
import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import retrofit2.Retrofit;

/**
 * Created by Steve on 07/08/2015.
 */
public class MainActivity extends AppCompatActivity implements
    NavigationView.OnNavigationItemSelectedListener {

    //UI elements
    private Toolbar toolbar;
    private ViewPager pager;
    private SlidingTabLayout tabs;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    Menu navigationMenu;

    //Defining Variables
    String[] tabTitles;
    TextView organisationName;
    public static final String ENDPOINT = "http://192.168.153.1/kaizen/KaizenMed/";
    PushNotifications push;
    PushNotificationsHandler pushHandler;

    @Override
    protected void onResume() {
        super.onResume();
        AppBus.getInstance().register(this);
        AppBus.getInstance().register(new ApiService(buildApi(), AppBus.getInstance()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppBus.getInstance().unregister(this);
    }

    private API buildApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .build();

        return  retrofit.create(API.class);
    }

    @Override
    protected void onStop() {
        super.onStop();
        push.interrupt();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabTitles = new String[2];

        if(AppPreferences.getInstance(this).getEcoCashWallet() != AppPreferences.getInstance(this).DEFAULT_VALUE_STRING){
            tabTitles[0] = "EcoCash "+AppPreferences.getInstance(this).getEcoCashWallet();
            if(AppPreferences.getInstance(this).getTeleCashWallet() != AppPreferences.getInstance(this).DEFAULT_VALUE_STRING){
                tabTitles[1] = "TeleCash "+AppPreferences.getInstance(this).getTeleCashWallet();
            }
        }else if(AppPreferences.getInstance(this).getTeleCashWallet() != AppPreferences.getInstance(this).DEFAULT_VALUE_STRING){
            tabTitles[0] = "TeleCash "+AppPreferences.getInstance(this).getTeleCashWallet();
            if(AppPreferences.getInstance(this).getEcoCashWallet() != AppPreferences.getInstance(this).DEFAULT_VALUE_STRING){
                tabTitles[1] = "EcoCash "+AppPreferences.getInstance(this).getEcoCashWallet();
            }
        }



        //intialize tabs
        pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tabs = (SlidingTabLayout)findViewById(R.id.tabs);
        tabs.setBackgroundColor(getResources().getColor(R.color.PrimaryColor));
        tabs.setSelectedIndicatorColors(getResources().getColor(R.color.divider));
        tabs.setViewPager(pager);

        //set up push notifications result handler
        pushHandler = new PushNotificationsHandler(this);

        //set up push notifications
        push = new PushNotifications();
        push.start();

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        this.navigationMenu = navigationView.getMenu();

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(this);

        // Initializing Drawer Layout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        //initialise the ActionBarDrawerToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        organisationName = (TextView) findViewById(R.id.organisationName);

        organisationName.setText(AppPreferences.getInstance(this).getOrganisationName());

        onNavigationItemSelected(navigationMenu.findItem(R.id.status));
    }

    class PagerAdapter extends FragmentPagerAdapter{
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            switch (i) {
                case 0:
                    fragment = new TransactionsFragment();
                    break;
                case 1:
                    fragment = new TransactionsFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void handlePushNotifications(String msg) {
        Gson gson = new Gson();
        PatientsResults.JSONObject result = gson.fromJson(msg,
                PatientsResults.JSONObject.class);

        //create an array list and add the result to the list
        ArrayList<PatientsResults.JSONObject> results = new ArrayList<PatientsResults.JSONObject>();
        results.add(result);

        Toast.makeText(this, result.getName()+" "+result.getSurname()+" Results are ready.",
                Toast.LENGTH_LONG).show();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_atom)
                        .setContentTitle("New result.")
                        .setContentText(result.getName()+" "+result.getSurname()+" "+result.getCondition());
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra("notification",result);
// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    //Implementation of the Navigation View Item Selected Listener handling the item click of the navigation menu
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        Transaction t = new Transaction();
        //Checking if the item is in checked state or not, if not make it in checked state
        if(menuItem.isChecked()) menuItem.setChecked(false);
        else menuItem.setChecked(true);

        //Closing drawer on item click
        drawerLayout.closeDrawers();
        //Check to see which item was being clicked and perform appropriate action
        switch (menuItem.getItemId()) {
            case R.id.status:
                toolbar.setTitle("Status");
                t.setPaymentType("all");
                AppBus.getInstance().post(t);
                break;
            case R.id.incoming:
                toolbar.setTitle("Incoming");
                t.setPaymentType("Incoming Payment");
                AppBus.getInstance().post(t);
                break;
            case R.id.outgoing:
                toolbar.setTitle("Outgoing");
                t.setPaymentType("Outgoing Payment");
                AppBus.getInstance().post(t);
                break;
            case R.id.inventory:
                break;
            case R.id.accounts:
                break;
            case R.id.audit:
                break;
            case R.id.settings:
                break;
            default:
                break;
        }
        return true;
    }


    private static class PushNotificationsHandler extends Handler {

        private final WeakReference<MainActivity> currentActivity;

        public PushNotificationsHandler(MainActivity activity){
            currentActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message message){
            MainActivity activity = currentActivity.get();
            if (activity!= null){
                activity.handlePushNotifications(message.getData().getString("result"));
            }
        }
    }


    public class PushNotifications extends Thread {
        Context ctx;
        private final static String QUEUE_NAME = "hello";
        Connection connection;
        Channel channel;

        @Override
        public void run() {
            super.run();
            try {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setUsername("admin");
                factory.setPassword("admin");
                factory.setVirtualHost("/");
                factory.setHost("192.168.153.1");
                factory.setPort(5672);
                connection = factory.newConnection();
                channel = connection.createChannel();

                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                Log.d("RabbitFoot", "waiting for msgz :)");

                Consumer consumer = new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope,
                                               AMQP.BasicProperties properties, byte[] body)
                            throws IOException {
                        String message = new String(body, "UTF-8");

                        Bundle msgBundle = new Bundle();
                        msgBundle.putString("result", message);
                        Message msg = new Message();
                        msg.setData(msgBundle);
                        pushHandler.sendMessage(msg);


                        Log.d("RabbitFoot", message);
                    }
                };
                channel.basicConsume(QUEUE_NAME, true, consumer);
            } catch (IOException e) {
                Log.d("RabbitFoot", e.getCause() + " " + e.getMessage());
            } catch (TimeoutException e) {
                Log.d("RabbitFoot", e.getCause() + " " + e.getMessage());
            }


        }

        @Override
        public void interrupt() {
            super.interrupt();
            Log.d("RabbitFoot", "no longer listening :(");

            try {
                channel.close();
                connection.close();
            } catch (IOException e) {
                Log.d("RabbitFoot", e.getCause() + " " + e.getMessage());
            } catch (TimeoutException e) {
                Log.d("RabbitFoot", e.getCause() + " " + e.getMessage());
            }
        }


    }

}



        /*
        Fragment Switching


        public void swapFragments(Fragment f) {
            //FragmentTransaction fragmentTransaction = getSupportFragmentManager()
            //        .beginTransaction()
            //        .replace(R.id.frame, f)
            //        .addToBackStack("result");
            //fragmentTransaction.commit();
        }

        //android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.replace(R.id.frame, fragment);
        //fragmentTransaction.commit();
        */
