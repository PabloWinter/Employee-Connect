package ca.bvc.employeeconnect.viewmodel;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import ca.bvc.employeeconnect.remote.FirebaseQueryLiveData;

public class RequestViewModel {

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(db.collection("dayOff"));

    @Nonnull
    public LiveData<QuerySnapshot> getRequestsDayOff() {
        return liveData;
    }

    public void sendRequestDayOff(final Context context, String user, String employeeCode, String date, String reason){

        Map<String, Object> employeeRequest = new HashMap<>();
        employeeRequest.put("user", user);
        employeeRequest.put("date", date );
        employeeRequest.put("employeeCode", employeeCode);
        employeeRequest.put("reason", reason);

        db.collection("dayOff")
                .add(employeeRequest)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(context, "The request was been submitted successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                    }
                });
    }

}
