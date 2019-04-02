package ca.bvc.employeeconnect;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toolbar;

public class UserScheduleForm extends AppCompatActivity {

    TextView date;
    TextView employeeName;
    TextView shiftHours;
    TextView note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_schedule_form);
        Intent intent = getIntent();
        this.setTitle(intent.getStringExtra("WEEK_DAY") + " Schedule");
        date = (TextView)findViewById(R.id.date_textview_id);
        employeeName = (TextView)findViewById(R.id.employee_name_textview_id);
        shiftHours = (TextView)findViewById(R.id.shift_hours_textview_id);
        note = (TextView)findViewById(R.id.note_textview_id);
        date.setPaintFlags(date.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        date.setText(intent.getStringExtra("DATE"));
        employeeName.setText("Name");
        shiftHours.setText("Shift Hours");
        note.setText("Note");
    }
}
