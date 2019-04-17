package ca.bvc.employeeconnect;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.type.Date;

import java.util.Calendar;

import ca.bvc.employeeconnect.model.RequestDayOff;
import ca.bvc.employeeconnect.model.User;
import ca.bvc.employeeconnect.viewmodel.MessageViewModel;
import ca.bvc.employeeconnect.viewmodel.RequestViewModel;
import ca.bvc.employeeconnect.viewmodel.UserViewModel;

public class RequestDayOffActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private Button buttonGetDate;
    private TextView dateText;
    private EditText employeeCode;
    private EditText employeeReason;
    private Button submitEmployee;
    private Button cancelForm;
    private Button acceptRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_day_off);

        buttonGetDate = findViewById(R.id.enter_new_date);
        dateText = findViewById(R.id.date_text);
        employeeReason = findViewById(R.id.employee_reason);
        submitEmployee = findViewById(R.id.submit_employee_form);

        buttonGetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RequestDayOffActivity.this, android.R.style.Theme_DeviceDefault_Dialog_Alert, onDateSetListener, year, month, day);
                //datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });

        // Event listener to select the date for a day off
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = "Date: " +  year + "/" + month + "/" + dayOfMonth;
                dateText.setText(date);
            }
        };

        submitEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instance of view model to submit employee application
                RequestViewModel requestViewModel = new RequestViewModel();
                // Getting users name from user view model
                User user = UserViewModel.getUser(RequestDayOffActivity.this);
                // Assign user name to use it as a parameter
                String userName = user.getName();
                String codeEmployee = employeeCode.getText().toString();
                String reason = employeeReason.getText().toString();
                String date = dateText.getText().toString();

                if (userName.isEmpty()  || codeEmployee.isEmpty() || reason.isEmpty() || date.isEmpty()){
                    Toast.makeText(RequestDayOffActivity.this, "Error, fields cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }

                else {
                    // Send request to Firestore
                    requestViewModel.sendRequestDayOff(RequestDayOffActivity.this, userName, codeEmployee, date, reason);
                }
            }
        });
    }
}
