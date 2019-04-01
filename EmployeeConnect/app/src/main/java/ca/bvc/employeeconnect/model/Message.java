package ca.bvc.employeeconnect.model;

import com.google.firebase.Timestamp;

import androidx.annotation.NonNull;

public class Message {
    private String SenderName, SenderId, MessageBody;
    private Timestamp timestamp;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Message(String senderId, String messageBody, Timestamp timestamp) {
        SenderId = senderId;
        MessageBody = messageBody;
        this.timestamp = timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getMessageBody() {
        return MessageBody;
    }

    public void setMessageBody(String messageBody) {
        MessageBody = messageBody;
    }

    @NonNull
    @Override
    public String toString() {
        return getSenderId() + " -> " + getMessageBody() + " -> "  + getTimestamp();
    }
}
