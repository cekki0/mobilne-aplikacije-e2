package com.example.fijiapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.activity.NotificationViewActivity;
import com.example.fijiapp.activity.StaffDetailActivity;
import com.example.fijiapp.activity.register.RegistrationRequestsManagement;
import com.example.fijiapp.model.Notification;
import com.example.fijiapp.model.User;
import com.example.fijiapp.service.NotificationService;
import com.example.fijiapp.service.UserService;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<Notification> notifications;
    private Context context;
    private NotificationService notificationService = new NotificationService();


    public NotificationAdapter(List<Notification> dataSet, Context context) {
        this.notifications = dataSet;
        this.context = context;

    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card, parent, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        Log.d("WTF", notification.Message);
        holder.title.setText("Title: " + notification.Title);
        holder.message.setText("Message: " + notification.Message);
        holder.date.setText("Date: " + notification.date);
        holder.sender.setText("Sender: " + notification.Sender.getFullName());

        holder.seenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification.HasSeen=true;
                notificationService.updateNotification(notification);
                Intent intent = new Intent(context, NotificationViewActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void filterList(List<Notification> filteredList) {
        notifications = filteredList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView message;
        TextView date;
        TextView sender;
        Button seenButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTextView);
            message = itemView.findViewById(R.id.messageTextView);
            date = itemView.findViewById(R.id.dateTextView);
            sender = itemView.findViewById(R.id.senderTextView);
            seenButton = itemView.findViewById(R.id.seenButton);
        }
    }
}