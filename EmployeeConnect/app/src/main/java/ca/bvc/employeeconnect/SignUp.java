package ca.bvc.employeeconnect;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // FireStore instance database
        FirebaseFirestore db = FirebaseFirestore.getInstance();

    }

    public void openLogin(View view) {
        finish();
    }

    public void registerUser(View view) {
        Intent logInIntent = new Intent(this, Home.class);
        startActivity(logInIntent);
    }
}
