package ca.bvc.employeeconnect.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import ca.bvc.employeeconnect.helper.MyDate;
import ca.bvc.employeeconnect.remote.FirebaseQueryLiveData;

public class ScheduleViewModel extends ViewModel {

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(db.collection("users"));

    //get all user from firebase.
    @Nonnull
    public LiveData<QuerySnapshot> getUserList() {
        return liveData;
    }

    /**
     * method to add schedule to database
     *     //add schedule to the firebase,
     *     //if success then return true,
     *     //if not then return false.
     * @param context
     * @param userId
     * @param storeId
     * @param startTime
     * @param endTime
     * @param note
     * @param title
     * @param selectedDate
     */
    public void addSchedule(final Context context, String userId, String storeId, String startTime, String endTime, String note, String title, Date selectedDate){


        if (userId.equals("") || storeId.equals("") || startTime.equals("") || endTime.equals("") || title.equals("")) {
            Toast.makeText(context, "Please fill all the field first.", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, Object> userSchedule = new HashMap<>();
        userSchedule.put("EndTime", endTime);
        userSchedule.put("Name", title);
        userSchedule.put("Note", note);
        userSchedule.put("StartTime", startTime);
        userSchedule.put("StoreId", storeId);
        userSchedule.put("TimeStamp", MyDate.getTimeStamp(selectedDate));
        userSchedule.put("Uid", userId);

        db.collection("events")
                .add(userSchedule)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(context, "Schedule has been updated.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
