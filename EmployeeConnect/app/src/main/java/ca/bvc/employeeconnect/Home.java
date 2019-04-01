package ca.bvc.employeeconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
            UserProfileFragment.OnFragmentInteractionListener,
            MessageFragment.OnFragmentInteractionListener,
            ScheduleFragment.OnFragmentInteractionListener{

    //fragment object
    Fragment fragment;

    private Context mContext;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private static final int RC_SIGN_IN = 1;

    List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build());

    private String TAG_SCHEDULE_FRAGMENT = "ca.bvc.employeeconnect.schedule.fragment";
    private String TAG_MESSAGE_FRAGMENT = "ca.bvc.employeeconnect.message.fragment";
    private String TAG_USER_PROFILE_FRAGMENT = "ca.bvc.employeeconnect.profile.fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initialize props
        mContext = this;

        //handle user sign in logic
        initAuth();

        //set up drawer in view
        setUpDrawer();

        //initialize navigation view
        initNavigation();
    }

    private void initNavigation() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //initialize default UI
        fragment = new ScheduleFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment, TAG_SCHEDULE_FRAGMENT).commit();
    }

    private void setUpDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null ) {
                    Toast.makeText(mContext, "User Sign In", Toast.LENGTH_SHORT).show();
                } else  {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .setIsSmartLockEnabled(false)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }

    @Override
    public void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthListner);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
        String tag;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (selectedOption == R.id.navigation_message) {
            tag = TAG_MESSAGE_FRAGMENT;
            fragment = fragmentManager.findFragmentByTag(tag);
            if (fragment == null) {
                fragment = new MessageFragment();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment, tag).addToBackStack(TAG_SCHEDULE_FRAGMENT).commit();
        } else if (selectedOption == R.id.navigation_profile){
            tag = TAG_USER_PROFILE_FRAGMENT;
            fragment = fragmentManager.findFragmentByTag(tag);
            if (fragment == null) {
                fragment = new UserProfileFragment();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment, tag).addToBackStack(TAG_SCHEDULE_FRAGMENT).commit();
        } else if (selectedOption == R.id.navigation_logout) {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(mContext, "Log Out", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            tag = TAG_SCHEDULE_FRAGMENT;
            fragment = fragmentManager.findFragmentByTag(tag);
            if (fragment == null) {
                fragment = new ScheduleFragment();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment, tag).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
