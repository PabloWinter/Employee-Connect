package ca.bvc.employeeconnect.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nonnull;

import ca.bvc.employeeconnect.model.User;
import ca.bvc.employeeconnect.remote.FirebaseQueryLiveData;


public class UserViewModel extends ViewModel {

    private static final Query query = FirebaseFirestore.getInstance().collection("users");

    private FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(query);

    @Nonnull
    public LiveData<QuerySnapshot> getDataSnapshotLiveData() {
        return liveData;
    }

}
