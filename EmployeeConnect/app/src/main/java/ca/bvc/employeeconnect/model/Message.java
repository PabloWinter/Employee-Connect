package ca.bvc.employeeconnect.model;

import android.support.annotation.NonNull;

import com.google.firebase.Timestamp;


public class Message {
    private String senderName, senderId, messageBody;
    private Timestamp timestamp;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Message(String senderName, String messageBody, Timestamp timestamp) {
        this.senderName = senderName;
        this.messageBody = messageBody;
        this.timestamp = timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

}
