package ca.bvc.employeeconnect.viewmodel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import ca.bvc.employeeconnect.adapter.ChatListAdapter;
import ca.bvc.employeeconnect.adapter.RequestListAdapter;
import ca.bvc.employeeconnect.model.Message;
import ca.bvc.employeeconnect.model.RequestDayOff;
import ca.bvc.employeeconnect.remote.FirebaseQueryLiveData;

public class RequestViewModel {

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(db.collection("dayOff"));
    ArrayList<RequestDayOff> list;

    @Nonnull
    public LiveData<QuerySnapshot> getRequestsDayOff() {
        return liveData;
    }

    /**
     * method to submit day off request to database
     * @param context
     * @param user
     * @param date
     * @param reason
     */
    public void sendRequestDayOff(final Context context, String user,  String date, String reason){

        Map<String, Object> employeeRequest = new HashMap<>();
        employeeRequest.put("user", user);
        employeeRequest.put("date", date );
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

    /**
     * initialize recycler view for employee day off requests list
     * @param context
     * @param requestRecyclerView
     */
    public void initVRecycler(final Context context, final RecyclerView requestRecyclerView) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);

        liveData.observe((LifecycleOwner) context, new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(@Nullable QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots != null) {
                    List<RequestDayOff> list = new ArrayList<>();
                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        list.add(new RequestDayOff(doc.getString("user"), "1234567890", doc.getString("reason"), doc.getString("date")));
                    }
                    requestRecyclerView.setAdapter(new RequestListAdapter(context, list));
                    requestRecyclerView.setLayoutManager(layoutManager);
                }
            }
        });
    }
}
