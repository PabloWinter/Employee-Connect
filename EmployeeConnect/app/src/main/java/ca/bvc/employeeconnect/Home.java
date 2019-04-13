package ca.bvc.employeeconnect;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.List;

import ca.bvc.employeeconnect.model.User;
import ca.bvc.employeeconnect.viewmodel.UserViewModel;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
            UserProfileFragment.OnFragmentInteractionListener,
            MessageFragment.OnFragmentInteractionListener,
            ScheduleFragment.OnFragmentInteractionListener{

    //fragment object
    Fragment fragment;

    private Context mContext;

    private static final int RC_EMPLOYEE_SIGN_IN = 1;


    private String TAG_SCHEDULE_FRAGMENT = "ca.bvc.employeeconnect.schedule.fragment";
    private String TAG_MESSAGE_FRAGMENT = "ca.bvc.employeeconnect.message.fragment";
    private String TAG_USER_PROFILE_FRAGMENT = "ca.bvc.employeeconnect.profile.fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initialize props
        mContext = this;

        //set up drawer in view
        setUpDrawer();

        //initialize navigation view
        initNavigation();

        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        showUserInfo(userViewModel.getUser(this));
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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    private void showUserInfo(User user) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView navUserName = (TextView)hView.findViewById(R.id.userName);
        TextView navUserEmail = (TextView)hView.findViewById(R.id.userEmail);
        ImageView navUserImage = (ImageView)hView.findViewById(R.id.userImage);

        if (user != null) {
            navUserName.setText(user.getName());
            navUserEmail.setText(user.getEmail());
            Glide.with(mContext)
                    .load(user.getPhotoUrl())
                    .error(R.drawable.ic_menu_home)
                    .into(navUserImage);
        }
    }

    private void initAuth() {
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        if (userViewModel.getUser(this) == null) {
            //start Login Activity
            Intent loginIntent = new Intent(mContext, LoginActivity.class);
            startActivityForResult(loginIntent, RC_EMPLOYEE_SIGN_IN);
        } else {
            showUserInfo(userViewModel.getUser(this));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
            logOutUser();
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

    private void logOutUser() {
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.logOutUser(this);
        Intent loginIntent = new Intent(mContext, LoginActivity.class);
        startActivityForResult(loginIntent, RC_EMPLOYEE_SIGN_IN);
        finish();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
