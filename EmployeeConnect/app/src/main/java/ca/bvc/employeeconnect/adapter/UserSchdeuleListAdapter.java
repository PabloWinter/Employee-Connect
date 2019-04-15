package ca.bvc.employeeconnect.adapter;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ca.bvc.employeeconnect.R;
import ca.bvc.employeeconnect.UserScheduleForm;
import ca.bvc.employeeconnect.model.User;
import ca.bvc.employeeconnect.viewmodel.ScheduleViewModel;

public class UserSchdeuleListAdapter extends RecyclerView.Adapter<UserSchdeuleListAdapter.ScheduleListHolder> {

    Context mContext;
    private List<User> mUsers;
    TimePickerDialog timePickerDialog;
    ScheduleViewModel scheduleViewModel;
    Date selectedDate;

    public UserSchdeuleListAdapter(Context context, List<User> users, Date finalConvertedDate){
        this.mContext = context;
        this.mUsers = users;
        scheduleViewModel = new ScheduleViewModel();
        this.selectedDate = finalConvertedDate;
    }

    @NonNull
    @Override
    public ScheduleListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_schedule_recyclerview_layout, viewGroup, false);
        ScheduleListHolder scheduleForm = new ScheduleListHolder(view);
        return scheduleForm;
    }

    @Override
    public void onBindViewHolder(@NonNull final ScheduleListHolder scheduleListHolder, final int i) {

        scheduleListHolder.userName.setText(mUsers.get(i).getName());

        //set onclick listener for startTime Textview.
        scheduleListHolder.startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(hourOfDay <= 12){
                            scheduleListHolder.startTime.setText("Start Time : " + hourOfDay + ":" + minute + " am");
                        }
                        else{
                            scheduleListHolder.startTime.setText("Start Time : " + hourOfDay + ":" + minute + " pm");
                        }

                    }
                }, 0, 0, false);
                timePickerDialog.show();
            }
        });

        //set onclick listener for endTime Textview.
        scheduleListHolder.endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int currentHours = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMin = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(hourOfDay <= 12){
                            scheduleListHolder.endTime.setText("End Time : " + hourOfDay + ":" + minute + " am");
                        }
                        else{
                            scheduleListHolder.endTime.setText("End Time : " + hourOfDay + ":" + minute + " pm");
                        }
                    }
                }, currentHours, currentMin, false);
                timePickerDialog.show();
            }
        });

        //set onclick listener for button to add schedule to the firebase.
        scheduleListHolder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!scheduleListHolder.startTime.getText().equals("") && !scheduleListHolder.endTime.getText().equals("")){
                    scheduleViewModel.addSchedule(mContext, mUsers.get(i).getId() , mUsers.get(i).getStoreId(), scheduleListHolder.startTime.getText().toString()
                            ,scheduleListHolder.endTime.getText().toString(),scheduleListHolder.note.getText().toString(),scheduleListHolder.title.getText().toString(),selectedDate);
                }
                else{
                    Toast.makeText(mContext, "Please fill the fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ScheduleListHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView startTime;
        TextView endTime;
        EditText note;
        EditText title;
        Button addBtn;

        public ScheduleListHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.username_id);
            startTime = itemView.findViewById(R.id.start_time_id);
            endTime = itemView.findViewById(R.id.end_time_id);
            note = itemView.findViewById(R.id.note_id);
            title = itemView.findViewById(R.id.shift_id);
            addBtn = itemView.findViewById(R.id.add_button_id);
        }
    }
}
