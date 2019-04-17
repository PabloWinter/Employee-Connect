package ca.bvc.employeeconnect.remote;

import android.arch.lifecycle.LiveData;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseQueryLiveData extends LiveData<QuerySnapshot> {

    private final String TAG_FIRESTORE_LIVE_DATA = "firestore.livedata";

    private final MyValueEventListener myValueEventListener = new MyValueEventListener();

    private Query mQuery;

    private ListenerRegistration mListenerRegistration;

    private boolean listenerRemovePending = false;
    private final Handler handler = new Handler();

    /**
     * Constructor
     * @param query
     */
    public FirebaseQueryLiveData(Query query) {
        mQuery = query;
    }

    /**
     * Remove any registered Listener
     */
    private final Runnable removeListener = new Runnable() {
        @Override
        public void run() {
            mListenerRegistration.remove();
            listenerRemovePending = false;
        }
    };

    /**
     * register listener
     */
    @Override
    public void onActive() {
        super.onActive();
        if (listenerRemovePending) {
            handler.removeCallbacks(removeListener);
        } else {
            mListenerRegistration = mQuery.addSnapshotListener(myValueEventListener);
        }
        listenerRemovePending = false;
    }

    /**
     * remove listener onInactive
     */
    @Override
    protected void onInactive() {
        super.onInactive();

        Log.d(TAG_FIRESTORE_LIVE_DATA, "onInactive: ");
        // Listener removal is schedule on a two second delay
        handler.postDelayed(removeListener, 2000);
        listenerRemovePending = true;
    }

    /**
     * class to set up event listener on query
     */
    private class MyValueEventListener implements EventListener<QuerySnapshot> {
        @Override
        public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
            if (e != null){
                Log.e(TAG_FIRESTORE_LIVE_DATA, "Can't listen to query snapshots: " + querySnapshot + ":::" + e.getMessage());
                return;
            }
            setValue(querySnapshot);
        }
    }
}

