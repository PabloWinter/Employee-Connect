package ca.bvc.employeeconnect.viewmodel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import ca.bvc.employeeconnect.adapter.ChatListAdapter;
import ca.bvc.employeeconnect.model.Message;
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

    public void initChatSendMessageListner(final Context context, final TextView inputTextView, final ImageButton sendMessageBtn) {
        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textMessage = inputTextView.getText().toString();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Map<String, Object> chatMessage = new HashMap<>();
                chatMessage.put("SenderName", user.getDisplayName());
                chatMessage.put("Text", textMessage);
                chatMessage.put("TimeStamp", new com.google.firebase.Timestamp(new Date()));

                db.collection("messages")
                        .add(chatMessage)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                inputTextView.setText("");
                                Toast.makeText(context, "Your message was sent", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                inputTextView.setText("");
                                Toast.makeText(context, "Could not send message", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }

    public void initChatRecyclerView(final Context context, final RecyclerView chatRecyclerView) {
        final LinearLayoutManager chatLinearLayoutManager = new LinearLayoutManager(context);

        liveData.observe((LifecycleOwner) context, new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(@Nullable QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots != null) {
                    ArrayList<Message> messages = new ArrayList<>();
                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        messages.add(new Message(doc.getString("SenderName"), doc.getString("Text"), doc.getTimestamp("TimeStamp")));
                    }
                    messages.sort(new Comparator<Message>() {
                        @Override
                        public int compare(Message o1, Message o2) {
                            return o1.getTimestamp().compareTo(o2.getTimestamp());
                        }
                    });
                    chatRecyclerView.setAdapter(new ChatListAdapter(context, messages));
                    if (chatRecyclerView.getLayoutManager() == null) {
                        chatRecyclerView.setLayoutManager(chatLinearLayoutManager);
                    }
                }
            }
        });
    }
}
