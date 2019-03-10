package ca.bvc.employeeconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView loginImage;
    Button logInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        loginImage = (ImageView)findViewById(R.id.imageView_id);
//        //loginImage.setImageResource(R.drawable.login_image);
//        logInButton = (Button)findViewById(R.id.login_button_id);
//        logInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, Navigation.class);
//                startActivity(intent);
//            }
//        });
    }

    public void openSignUp(View view) {
        Intent signUpIntent = new Intent(this, SignUp.class);
        startActivity(signUpIntent);
    }

    public void authenticateUser(View view) {
        Intent logInIntent = new Intent(this, Navigation.class);
        startActivity(logInIntent);
    }
}
