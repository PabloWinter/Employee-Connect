package ca.bvc.employeeconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
            UserProfileFragment.OnFragmentInteractionListener,
            MessageFragment.OnFragmentInteractionListener,
            ScheduleFragment.OnFragmentInteractionListener{

    //preference to save user info locally
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "ca.bvc.employeeconnect.userprefrences";

    //keys for the preferences
    private final String FIRST_NAME = "ca.bvc.employeeconnect.fname";
    private final String LAST_NAME = "ca.bvc.employeeconnect.lname";
    private final String EMAIL = "ca.bvc.employeeconnect.email";
    private final String MANAGER = "ca.bvc.employeeconnect.manager";
    private final String STORE_ID = "ca.bvc.employeeconnect.storeid";

    //fragment object
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //set the preference
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        if (savedInstanceState == null) {
            Intent logInIntent = new Intent(this, LoginActivity.class);
            logInIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(logInIntent);
        }

        fragment = new ScheduleFragment();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        //to do change hardcoded values with database values
        preferencesEditor.putString(FIRST_NAME, "Brijesh");
        preferencesEditor.putString(LAST_NAME, "Patel");
        preferencesEditor.putString(EMAIL, "b.patel405@mybvc.ca");
        preferencesEditor.putBoolean(MANAGER, true);
        preferencesEditor.putInt(STORE_ID, 000111);
        preferencesEditor.apply();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home button.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int selectedOption = item.getItemId();

        boolean doesFragmentexist = false;

        if (selectedOption == R.id.navigation_schedule) {
            fragment = new ScheduleFragment();
            doesFragmentexist = true;
        } else if (selectedOption == R.id.navigation_message) {
            fragment = new MessageFragment();
            doesFragmentexist = true;
        } else if (selectedOption == R.id.navigation_profile){
            fragment = new UserProfileFragment();
            doesFragmentexist = true;
        }
        if (doesFragmentexist){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).addToBackStack(null).commit();
        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
