package ca.bvc.employeeconnect;

import android.app.Activity;
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

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 7;
    public static final String ADMIN_TAG = "admin";

    private List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(), new AuthUI.IdpConfig.GoogleBuilder().build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void verifyEmployerCode(View view) {
        final Context context = this;
        EditText editText = ((Activity) context).findViewById(R.id.secret_key);
        String userEmployerCodeInput = editText.getText().toString();
        if (userEmployerCodeInput.equals(getText(R.string.employee_secret_key))) {
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(ADMIN_TAG, false);
            editor.commit();
            initFirebaseAuthRegister();
        } else if (userEmployerCodeInput.equals(getText(R.string.admin_secret_key))) {
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(ADMIN_TAG, true);
            editor.commit();
            initFirebaseAuthRegister();
        }
        else {
            Toast.makeText(context,  getString(R.string.wrong_employer_code_msg), Toast.LENGTH_SHORT).show();
        }
    }

    private void initFirebaseAuthRegister() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Intent intent = getIntent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
