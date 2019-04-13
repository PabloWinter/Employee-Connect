package ca.bvc.employeeconnect.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import ca.bvc.employeeconnect.remote.FirebaseQueryLiveData;

public class ScheduleViewModel extends ViewModel {

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(db.collection("users"));

    @Nonnull
    public LiveData<QuerySnapshot> getUserList() {
        return liveData;
    }

    public void addSchedule(String userId, String storeId, String startTime, String endTime, String note, String title, final OnSuccessListener<DocumentReference> successListener, final OnFailureListener failureListener){
        Map<String, Object> userSchedule = new HashMap<>();
        userSchedule.put("EndTime", endTime);
        userSchedule.put("Name", title);
        userSchedule.put("Note", note);
        userSchedule.put("StartTime", startTime);
        userSchedule.put("StoreId", storeId);
        userSchedule.put("TimeStamp", new com.google.firebase.Timestamp(new Date()));
        userSchedule.put("Uid", userId);

        db.collection("events")
                .add(userSchedule)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        successListener.onSuccess(documentReference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        failureListener.onFailure(e);
                    }
                });
    }
}
