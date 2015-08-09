package com.afrikaizen.kaizenmed.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afrikaizen.kaizenmed.R;
import com.afrikaizen.kaizenmed.controllers.PatientResultFragment;
import com.afrikaizen.kaizenmed.controllers.PatientResultsFragment;
import com.afrikaizen.kaizenmed.models.PatientsResults;
import com.afrikaizen.kaizenmed.rest.API;
import com.afrikaizen.kaizenmed.rest.ApiService;
import com.afrikaizen.kaizenmed.singleton.AppBus;
import com.afrikaizen.kaizenmed.singleton.AppPreferences;
import com.squareup.otto.Subscribe;

import java.lang.ref.WeakReference;

import retrofit.RestAdapter;

/**
 * Created by Steve on 07/08/2015.
 */
public class MainActivity extends AppCompatActivity implements
    NavigationView.OnNavigationItemSelectedListener{

    //Defining Variables
    TextView doctorsName, ward;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    Menu navigationMenu;
    public static final String ENDPOINT = "http://www.afrikaizen.com/kaizenmed";

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

    private API buildApi(){
        return new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(API.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

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

        doctorsName = (TextView)findViewById(R.id.username_header);
        ward = (TextView)findViewById(R.id.ward);

        doctorsName.setText(AppPreferences.getInstance(this).getDoctorsName());
        ward.setText("Ward Number: " + AppPreferences.getInstance(this).getDoctorsID());

        if(savedInstanceState == null){
            onNavigationItemSelected(navigationMenu.findItem(R.id.patient_results));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
            if(menuItem.isChecked()) menuItem.setChecked(false);
            else menuItem.setChecked(true);

            //Closing drawer on item click
            drawerLayout.closeDrawers();
        Fragment fragment = new Fragment();
            //Check to see which item was being clicked and perform appropriate action
            switch (menuItem.getItemId()){
                //Replacing the main content with ContentFragment Which is our Inbox View;
                case R.id.patient_results:
                    fragment = new PatientResultsFragment();
                    break;
                case R.id.patient_history:
                    Toast.makeText(getApplicationContext(),"Patient History",Toast.LENGTH_SHORT).show();
                    //fragment = new PatientHistoryFragment();
                    break;
                case R.id.notifications:
                    Toast.makeText(getApplicationContext(),"Notifications",Toast.LENGTH_SHORT).show();
                    //fragment = new NotificationsFragment();
                    break;
                case R.id.my_patients:
                    Toast.makeText(getApplicationContext(),"My Patients",Toast.LENGTH_SHORT).show();
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
}
