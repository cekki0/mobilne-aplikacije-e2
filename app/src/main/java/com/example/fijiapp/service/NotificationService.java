package com.example.fijiapp.service;

import com.example.fijiapp.model.Notification;
import com.example.fijiapp.repository.NotificationRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private NotificationRepository notificationRepository;

    public NotificationService() {
        notificationRepository = new NotificationRepository();
    }

    public Task<DocumentReference> addNotification(Notification notification) {
        return notificationRepository.addNotification(notification);
    }

    public Task<Void> updateNotification(Notification notification) {
        return notificationRepository.updateNotification(notification);
    }

    public Task<Void> deleteNotification(Notification notification) {
        return notificationRepository.deleteNotification(notification);
    }

    public Task<List<Notification>> getAllNotifications() {
        return notificationRepository.getAllNotifications().continueWith(task -> {
            if (task.isSuccessful()) {
                List<Notification> notifications = new ArrayList<>();
                for (DocumentSnapshot document : task.getResult()) {
                    Notification notification = document.toObject(Notification.class);
                    notification.Id = document.getId();
                    notifications.add(notification);
                }
                return notifications;
            } else {
                throw task.getException();
            }
        });
    }
}