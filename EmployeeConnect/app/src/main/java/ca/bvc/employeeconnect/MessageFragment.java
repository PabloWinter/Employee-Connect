package ca.bvc.employeeconnect;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Timestamp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ca.bvc.employeeconnect.adapter.ChatListAdapter;
import ca.bvc.employeeconnect.model.Message;
import ca.bvc.employeeconnect.viewmodel.MessageViewModel;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class MessageFragment extends Fragment {

    private Context mContext;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView chatRecyclerView;
    private MessageViewModel messageViewModel;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        mContext = this.getContext();
        getActivity().setTitle("Communication");
        chatRecyclerView = view.findViewById(R.id.communication_recycler_container);
        initChatRecyclerView();
        initChatSendMessageListner(view);
        return view;
    }

    private void initChatSendMessageListner(View view) {
        ImageButton sendButton = view.findViewById(R.id.button_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView inputTextView = (TextView) getActivity().findViewById(R.id.input_message);
                String textMessage = inputTextView.getText().toString();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                messageViewModel.sendMessage(user.getDisplayName(), textMessage, new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                inputTextView.setText("");
                                Toast.makeText(getActivity(), "Your message was sent", Toast.LENGTH_LONG).show();
                            }
                        }
                        , new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                inputTextView.setText("");
                                Toast.makeText(getActivity(), "Could not send message", Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }
        });
    }

    private void initChatRecyclerView() {
        final LinearLayoutManager chatLinearLayoutManager = new LinearLayoutManager(mContext);

        messageViewModel =  ViewModelProviders.of(this).get(MessageViewModel.class);

        LiveData<QuerySnapshot> messagesLiveData = messageViewModel.getMessages();
        messagesLiveData.observe(this, new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(@Nullable QuerySnapshot querySnapshot) {
                if (querySnapshot != null) {
                    ArrayList<Message> messages = new ArrayList<>();
                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        messages.add(new Message(doc.getString("SenderName"), doc.getString("Text"), doc.getTimestamp("TimeStamp")));
                    }
                    messages.sort(new Comparator<Message>() {
                        @Override
                        public int compare(Message o1, Message o2) {
                            return o1.getTimestamp().compareTo(o2.getTimestamp()) ;
                        }
                    });
                    chatRecyclerView.setAdapter(new ChatListAdapter(mContext, messages));
                    if (chatRecyclerView.getLayoutManager() == null) {
                        chatRecyclerView.setLayoutManager(chatLinearLayoutManager);
                    }
                }
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     *
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
