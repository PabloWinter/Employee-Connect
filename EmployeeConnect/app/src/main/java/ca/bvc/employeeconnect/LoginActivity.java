package ca.bvc.employeeconnect;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void openLogin(View view) {
        Intent signUpIntent = new Intent(this, SignUp.class);
        startActivity(signUpIntent);
    }

    public void registerUser(View view) {
        Intent logInIntent = new Intent(this, Navigation.class);
        startActivity(logInIntent);
    }
}
