package ca.bvc.employeeconnect;

import android.app.TimePickerDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import ca.bvc.employeeconnect.adapter.ChatListAdapter;
import ca.bvc.employeeconnect.adapter.UserSchdeuleListAdapter;
import ca.bvc.employeeconnect.model.Message;
import ca.bvc.employeeconnect.model.User;
import ca.bvc.employeeconnect.viewmodel.MessageViewModel;
import ca.bvc.employeeconnect.viewmodel.ScheduleViewModel;

public class UserScheduleForm extends AppCompatActivity {

    TextView date;
    RecyclerView recyclerView;
    ScheduleViewModel scheduleViewModel;
    UserSchdeuleListAdapter adapter;
    ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_schedule_form);
        Intent intent = getIntent();


        Date selectedDate = new Date(intent.getExtras().getLong(ScheduleFragment.EXTRA_SELECTED_DATE, -1));

        this.setTitle(selectedDate.toString());

        recyclerView = (RecyclerView)findViewById(R.id.user_recylerview_id);

        //converting date to timestamp
        String str_date = intent.getStringExtra("DATE");
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date convertedDate = null;
        try {
            convertedDate = (Date)formatter.parse(str_date);
            Log.d("timestamp", new com.google.firebase.Timestamp(convertedDate).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        scheduleViewModel = ViewModelProviders.of(this).get(ScheduleViewModel.class);
        LiveData<QuerySnapshot> userLiveData = scheduleViewModel.getUserList();

        final Date finalConvertedDate = convertedDate;
        userLiveData.observe(this, new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(@Nullable QuerySnapshot querySnapshot) {
                if (querySnapshot != null) {

                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        users.add(new User(doc.getString("Name"), doc.getId(), doc.getString("StoreId")));
                    }

                    adapter = new UserSchdeuleListAdapter(UserScheduleForm.this,users, finalConvertedDate);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
            }
        });
    }
}
