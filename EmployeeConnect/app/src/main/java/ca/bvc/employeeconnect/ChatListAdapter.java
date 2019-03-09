package ca.bvc.employeeconnect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ca.bvc.employeeconnect.library.Message;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListHolder> {

    private Context mContext;
    private ArrayList<Message> mMessages;

    public ChatListAdapter(Context context, ArrayList<Message> messages){
        mContext = context;
        mMessages = messages;
    }

    @NonNull
    @Override
    public ChatListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message, parent, false);
        ChatListHolder holder = new ChatListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListHolder gameListHolder, final int position) {
        gameListHolder.senderNameText.setText(mMessages.get(position).getSenderName());
        gameListHolder.timeStampText.setText(mMessages.get(position).getTimestamp());
        gameListHolder.messageBodyText.setText(mMessages.get(position).getMessageBody());
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public class ChatListHolder extends RecyclerView.ViewHolder {

        TextView senderNameText;
        TextView timeStampText;
        TextView messageBodyText;
        LinearLayout parentLayout;

        public ChatListHolder(@NonNull View itemView) {
            super(itemView);
            senderNameText = itemView.findViewById(R.id.chat_message_textView_senderName);
            timeStampText = itemView.findViewById(R.id.chat_message_textView_timestamp);
            messageBodyText = itemView.findViewById(R.id.chat_message_body);
            parentLayout = itemView.findViewById(R.id.chat_message_linearLayout);
        }
    }
}
