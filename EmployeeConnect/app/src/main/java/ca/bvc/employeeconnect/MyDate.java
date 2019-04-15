package ca.bvc.employeeconnect;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import com.google.firebase.Timestamp;


import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


public class MyDate {

    public static Timestamp getDate(int date, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return  new Timestamp(cal.getTime());
    }

    public static Timestamp getCurrentTimeStamp() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new Timestamp(cal.getTime());
    }
}
