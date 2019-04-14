package ca.bvc.employeeconnect;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import ca.bvc.employeeconnect.viewmodel.UserViewModel;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void signUpUser(View view) {
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        String employeeNumber = ((EditText)((Activity) this).findViewById(R.id.user_sign_up_employer_code)).getText().toString();
        String name = ((EditText)((Activity) this).findViewById(R.id.user_sign_up_name)).getText().toString();
        String email = ((EditText)((Activity) this).findViewById(R.id.user_sign_up_email)).getText().toString();
        String userId = ((EditText)((Activity) this).findViewById(R.id.user_sign_up_id)).getText().toString();
        String pin = ((EditText)((Activity) this).findViewById(R.id.user_sign_up_pin)).getText().toString();
        String confirmPin  = ((EditText)((Activity) this).findViewById(R.id.user_sign_up_confirm_pin)).getText().toString();
        userViewModel.registerAccount(this, employeeNumber, name, email, userId, pin, confirmPin);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void openLoginPage(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
