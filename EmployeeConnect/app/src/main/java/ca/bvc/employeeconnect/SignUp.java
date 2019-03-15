package ca.bvc.employeeconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void openLogin(View view) {
        finish();
    }

    public void registerUser(View view) {
        Intent logInIntent = new Intent(this, Home.class);
        startActivity(logInIntent);
    }
}
