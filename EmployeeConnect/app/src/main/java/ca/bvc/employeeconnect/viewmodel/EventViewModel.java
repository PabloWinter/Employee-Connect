package ca.bvc.employeeconnect.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;

import ca.bvc.employeeconnect.adapter.EventListAdapter;
import ca.bvc.employeeconnect.helper.MyDate;
import ca.bvc.employeeconnect.model.Event;
import ca.bvc.employeeconnect.model.User;
import ca.bvc.employeeconnect.remote.FirebaseQueryLiveData;

public class EventViewModel extends ViewModel {

    private static  final FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseQueryLiveData liveData;

    /**
     * initialize recycler view for events with live data for the event based on timestamp provided
     * @param context
     * @param eventRecyclerView
     * @param timestamp
     */
    public void initEventRecycler(final Context context, final RecyclerView eventRecyclerView,final Timestamp timestamp) {
        final LinearLayoutManager eventLinearLayoutManager = new LinearLayoutManager(context);

        UserViewModel userViewModel = ViewModelProviders.of((FragmentActivity) context).get(UserViewModel.class);
        User user = userViewModel.getUser((Activity) context);

        //remove previous listener
        if (this.liveData != null) {
            liveData.removeObservers((LifecycleOwner) context);
        }

        liveData = new FirebaseQueryLiveData(db.collection("events").whereEqualTo("Uid", user.getId()).whereEqualTo("TimeStamp", timestamp));

        //if admin show don't filter employee data
        if (user.isAdmin()) {
            liveData = new FirebaseQueryLiveData(db.collection("events").whereEqualTo("TimeStamp", timestamp));
        }

        //set listener for change in data and update content
        liveData.observe((LifecycleOwner) context, new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(@Nullable QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots != null) {
                    List<Event> events = new ArrayList<>();
                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        final Event event = new Event(doc.getString("Name"), doc.getString("Note"), doc.getString("StartTime"), doc.getString("EndTime"));
                        db.collection("users").document(doc.getString("Uid")).get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful() && task != null){
                                            event.setUserName("Employee : " + task.getResult().getString("Name"));
                                            eventRecyclerView.getAdapter().notifyDataSetChanged();
                                        }
                                    }
                                });
                        events.add(event);
                    }
                    eventRecyclerView.setAdapter(new EventListAdapter(context, events));
                    if (eventRecyclerView.getLayoutManager() == null) {
                        eventRecyclerView.setLayoutManager(eventLinearLayoutManager);
                    }
                }
            }
        });
    }
}
