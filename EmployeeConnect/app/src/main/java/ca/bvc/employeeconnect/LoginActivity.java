package ca.bvc.employeeconnect;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;
import java.util.List;

import ca.bvc.employeeconnect.viewmodel.MessageViewModel;
import ca.bvc.employeeconnect.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    private List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(), new AuthUI.IdpConfig.GoogleBuilder().build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        if (userViewModel.getUser(this) != null) {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
            finish();
        }
    }

    public void loginUser(View view) {
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        String userId = ((EditText)((Activity) this).findViewById(R.id.user_login_id)).getText().toString();
        String pin = ((EditText)((Activity) this).findViewById(R.id.user_login_pin)).getText().toString();
        userViewModel.authenticateUser(userId, pin, this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_SIGN_IN) {
//            Intent intent = getIntent();
//            setResult(RESULT_OK, intent);
//            finish();
//        }
    }
}
