package ca.bvc.employeeconnect.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nonnull;

import ca.bvc.employeeconnect.remote.FirebaseQueryLiveData;

public class EventViewModel extends ViewModel {

    private static  final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(db.collection("events"));

    @Nonnull
    public LiveData<QuerySnapshot> getEvents() {
        return liveData;
    }
}
