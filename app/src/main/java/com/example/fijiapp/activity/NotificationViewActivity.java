package com.example.fijiapp.activity;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fijiapp.R;
import com.example.fijiapp.adapters.NotificationAdapter;
import com.example.fijiapp.model.Notification;
import com.example.fijiapp.service.NotificationService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class NotificationViewActivity extends AppCompatActivity {
    private NotificationAdapter adapter;
    private List<Notification> notifications;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userId = FirebaseAuth.getInstance().getUid();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view);
        fetchAndInitializeRecyclerView();
    }


    private void fetchAndInitializeRecyclerView() {
        NotificationService notificationService = new NotificationService();
        notificationService.getAllNotificationsByUserId(userId)
                .addOnCompleteListener(new OnCompleteListener<List<Notification>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Notification>> task) {
                        if (task.isSuccessful()) {
                            notifications = task.getResult();
                            initializeRecyclerView(notifications);
                        } else {
                            Toast.makeText(NotificationViewActivity.this,
                                    "Failed to fetch requests", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initializeRecyclerView(List<Notification> notifications) {
        RecyclerView recyclerView = findViewById(R.id.notificationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NotificationAdapter(notifications, this);
        recyclerView.setAdapter(adapter);
    }
}