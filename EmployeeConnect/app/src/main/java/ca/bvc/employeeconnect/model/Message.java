package ca.bvc.employeeconnect.model;

import android.support.annotation.NonNull;

import com.google.firebase.Timestamp;


public class Message {
    private String senderName, senderId, messageBody;
    private Timestamp timestamp;

    /**
     * Constructor
     * @param senderName
     * @param messageBody
     * @param timestamp
     */
    public Message(String senderName, String messageBody, Timestamp timestamp) {
        this.senderName = senderName;
        this.messageBody = messageBody;
        this.timestamp = timestamp;
    }

    /**
     * get Time Stamp of message
     * @return
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * set TimeStamp for message
     * @param timestamp
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * get Sender Name
     * @return
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * set for setnder Name
     * @param senderName
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * get for message body
     * @return
     */
    public String getMessageBody() {
        return messageBody;
    }

    /**
     * set for message body
     * @param messageBody
     */
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

}
