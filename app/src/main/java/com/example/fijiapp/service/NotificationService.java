package com.example.fijiapp.service;

import android.util.Log;

import com.example.fijiapp.model.Company;
import com.example.fijiapp.model.Notification;
import com.example.fijiapp.model.User;
import com.example.fijiapp.repository.NotificationRepository;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private NotificationRepository notificationRepository;
    private UserService userService;

    public NotificationService() {
        notificationRepository = new NotificationRepository();
        userService = new UserService();
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

    public Task<List<Notification>> getAllNotificationsByUserId(String userId) {
        return notificationRepository.getAllNotifications().continueWithTask(task -> {
            if (task.isSuccessful()) {
                List<Task<Notification>> notificationTasks = new ArrayList<>();
                for (DocumentSnapshot document : task.getResult()) {
                    Notification notification = document.toObject(Notification.class);
                    notification.Id = document.getId();

                    if (notification.ReceiverId.equals(userId)) {
                        Task<User> userTask = userService.getUserById(notification.SenderId);
                        Task<Notification> notificationTask = userTask.continueWithTask(userTaskResult -> {
                            if (userTaskResult.isSuccessful()) {
                                notification.Sender = userTaskResult.getResult();
                            }
                            return Tasks.forResult(notification);
                        });
                        notificationTasks.add(notificationTask);
                    }
                }
                return Tasks.whenAllSuccess(notificationTasks);
            } else {
                throw task.getException();
            }
        }).continueWith(new Continuation<List<Object>, List<Notification>>() {
            @Override
            public List<Notification> then(Task<List<Object>> task) throws Exception {
                if (task.isSuccessful()) {
                    List<Object> resultList = task.getResult();
                    List<Notification> notifications = new ArrayList<>();
                    for (Object result : resultList) {
                        notifications.add((Notification) result);
                    }
                    return notifications;
                } else {
                    throw task.getException();
                }
            }
        });}
}