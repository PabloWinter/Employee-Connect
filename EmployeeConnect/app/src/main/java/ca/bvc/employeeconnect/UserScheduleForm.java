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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Date;
import ca.bvc.employeeconnect.adapter.UserSchdeuleListAdapter;
import ca.bvc.employeeconnect.model.User;
import ca.bvc.employeeconnect.viewmodel.ScheduleViewModel;

public class UserScheduleForm extends AppCompatActivity {

    RecyclerView recyclerView;
    ScheduleViewModel scheduleViewModel;
    UserSchdeuleListAdapter adapter;
    ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_schedule_form);
        Intent intent = getIntent();


        final Date selectedDate = new Date(intent.getExtras().getLong(ScheduleFragment.EXTRA_SELECTED_DATE, -1));

        this.setTitle(selectedDate.toString());

        recyclerView = (RecyclerView)findViewById(R.id.user_recylerview_id);


        scheduleViewModel = ViewModelProviders.of(this).get(ScheduleViewModel.class);
        LiveData<QuerySnapshot> userLiveData = scheduleViewModel.getUserList(this);

        userLiveData.observe(this, new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(@Nullable QuerySnapshot querySnapshot) {
                if (querySnapshot != null) {

                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        users.add(new User(doc.getString("Name"), doc.getId(), doc.getString("StoreId")));
                    }

                    adapter = new UserSchdeuleListAdapter(UserScheduleForm.this,users, selectedDate);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
            }
        });
    }
}
