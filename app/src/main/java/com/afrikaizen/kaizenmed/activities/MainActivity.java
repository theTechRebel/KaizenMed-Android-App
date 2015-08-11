package com.afrikaizen.kaizenmed.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.TaskStackBuilder;
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

import com.afrikaizen.kaizenmed.R;
import com.afrikaizen.kaizenmed.controllers.PatientResultFragment;
import com.afrikaizen.kaizenmed.controllers.PatientResultsFragment;
import com.afrikaizen.kaizenmed.models.PatientsResults;
import com.afrikaizen.kaizenmed.models.Results;
import com.afrikaizen.kaizenmed.rest.API;
import com.afrikaizen.kaizenmed.rest.ApiService;
import com.afrikaizen.kaizenmed.singleton.AppBus;
import com.afrikaizen.kaizenmed.singleton.AppPreferences;
import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import io.realm.Realm;
import retrofit.RestAdapter;

/**
 * Created by Steve on 07/08/2015.
 */
public class MainActivity extends AppCompatActivity implements
    NavigationView.OnNavigationItemSelectedListener {



    //Defining Variables
    TextView doctorsName, ward;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    Menu navigationMenu;
    public static final String ENDPOINT = "http://www.afrikaizen.com/kaizenmed";
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
        return new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(API.class);
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

        doctorsName = (TextView) findViewById(R.id.username_header);
        ward = (TextView) findViewById(R.id.ward);

        doctorsName.setText(AppPreferences.getInstance(this).getDoctorsName());
        ward.setText("Ward Number: " + AppPreferences.getInstance(this).getDoctorsID());

        if (savedInstanceState == null) {
            onNavigationItemSelected(navigationMenu.findItem(R.id.patient_results));
        }

        //get intent from notification if there
        PatientsResults.JSONObject result =
                (PatientsResults.JSONObject)getIntent().getSerializableExtra("notification");
        if(result != null){
            Fragment f = new PatientResultFragment();
            Bundle args = new Bundle();
            args.putSerializable(PatientResultFragment.RESULT, result);
            f.setArguments(args);
            swapFragments(f);
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

        //save the data to the db
        persistData(results);

        Toast.makeText(this, result.getName()+" "+result.getSurname()+" Results are ready.",
                Toast.LENGTH_LONG).show();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_inbox_black)
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

    //method used to persisit data in the realm db
    //listens from AppBus
    @Subscribe
    public void persistData(ArrayList<PatientsResults.JSONObject> results){
        int i = 0;
        for (PatientsResults.JSONObject result: results) {
            Realm realm = Realm.getInstance(this);
            realm.beginTransaction();
            Results r = realm.createObject(Results.class);
            r.setId(i);
            r.setName(result.getName());
            r.setSurname(result.getSurname());
            r.setCondition(result.getCondition());
            r.setResults(result.getResults());
            r.setTreated(result.getTreated());
            realm.commitTransaction();
            i++;
        }
        AppPreferences.getInstance(this).setDataPersisted("true");
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

    @Subscribe
    public void swapFragments(Fragment f) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, f)
                .addToBackStack("result");
        fragmentTransaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    //Implementation of the Navigation View Item Selected Listener handling the item click of the navigation menu
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        //Checking if the item is in checked state or not, if not make it in checked state
        if (menuItem.isChecked()) menuItem.setChecked(false);
        else menuItem.setChecked(true);

        //Closing drawer on item click
        drawerLayout.closeDrawers();
        Fragment fragment = new Fragment();
        //Check to see which item was being clicked and perform appropriate action
        switch (menuItem.getItemId()) {
            //Replacing the main content with ContentFragment Which is our Inbox View;
            case R.id.patient_results:
                fragment = new PatientResultsFragment();
                break;
            case R.id.patient_history:
                Toast.makeText(getApplicationContext(), "Patient History", Toast.LENGTH_SHORT).show();
                //fragment = new PatientHistoryFragment();
                break;
            case R.id.notifications:
                Toast.makeText(getApplicationContext(), "Notifications", Toast.LENGTH_SHORT).show();
                //fragment = new NotificationsFragment();
                break;
            case R.id.my_patients:
                Toast.makeText(getApplicationContext(), "My Patients", Toast.LENGTH_SHORT).show();
                //fragment = new MyPatientsFragment();
                break;
            default:
                break;
        }

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
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
                factory.setHost("192.168.1.101");
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
