package com.example.fijiapp.model;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Notification implements Serializable {
    @Exclude
    public String Id;
    public String Title;
    public String ReceiverId;
    public String SenderId;
    @Exclude
    public User Sender;
    public String Message;
    public String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    public Boolean HasSeen=false;

    public Notification(String title, String receiverId, String senderId, String message) {
        Title = title;
        ReceiverId = receiverId;
        SenderId = senderId;
        Message = message;
    }

    public Notification() {}
}
