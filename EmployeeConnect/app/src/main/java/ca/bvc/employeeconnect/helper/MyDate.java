package ca.bvc.employeeconnect.helper;

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

        return getDate(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
    }


    public static Timestamp getTimeStamp(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new Timestamp(cal.getTime());
    }
}
