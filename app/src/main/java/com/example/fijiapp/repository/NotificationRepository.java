package com.example.fijiapp.repository;

import com.example.fijiapp.model.Notification;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class NotificationRepository {
    private CollectionReference notificationRef;

    public NotificationRepository() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        notificationRef = database.collection("notifications");
    }

    public Task<DocumentReference> addNotification(Notification notification) {
        return notificationRef.add(notification);
    }

    public Task<Void> updateNotification(Notification notification) {
        String notificationId = notification.Id;
        if (notificationId == null) {
            return Tasks.forException(new IllegalArgumentException("Notification ID is null"));
        }

        return notificationRef.document(notificationId).set(notification);
    }

    public Task<Void> deleteNotification(Notification notification) {
        String notificationId = notification.Id;
        return notificationRef.document(notificationId).delete();
    }

    public Task<QuerySnapshot> getAllNotifications() {
        return notificationRef.get();
    }
}
