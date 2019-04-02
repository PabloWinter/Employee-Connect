package ca.bvc.employeeconnect.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import ca.bvc.employeeconnect.remote.FirebaseQueryLiveData;

public class MessageViewModel extends ViewModel {
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(db.collection("messages"));

    @Nonnull
    public LiveData<QuerySnapshot> getMessages() {
        return liveData;
    }

    public void sendMessage(String user, String message, final OnSuccessListener<DocumentReference> successListener, final OnFailureListener failureListener){
        Map<String, Object> chatMessage = new HashMap<>();
        chatMessage.put("SenderName", user);
        chatMessage.put("Text", message);
        chatMessage.put("TimeStamp", new com.google.firebase.Timestamp(new Date()));

        db.collection("messages")
                .add(chatMessage)
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
