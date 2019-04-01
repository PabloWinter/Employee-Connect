package ca.bvc.employeeconnect.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;


import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nonnull;

import ca.bvc.employeeconnect.remote.FirebaseQueryLiveData;

public class MessageViewModel extends ViewModel {
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(db.collection("messages"));

    @Nonnull
    public LiveData<QuerySnapshot> getMessages() {
        return liveData;
    }
}
