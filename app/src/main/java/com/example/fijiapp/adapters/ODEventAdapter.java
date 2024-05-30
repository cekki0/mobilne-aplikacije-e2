package com.example.fijiapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.model.ODEvent;

import java.util.List;

public class ODEventAdapter extends RecyclerView.Adapter<ODEventAdapter.ViewHolder> {
    private List<ODEvent> events;
    private Context context;

    public ODEventAdapter(List<ODEvent> dataSet, Context context) {
        this.events = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public ODEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.od_event_card, parent, false);
        return new ODEventAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ODEventAdapter.ViewHolder holder, int position) {
        ODEvent event = events.get(position);
        holder.eventNameView.setText("Name: " + event.EventName);
        holder.descriptionView.setText("Description: " + event.Description);
        holder.maxParticipantsView.setText("Max participants: " + event.MaxParticipants);
        holder.locationView.setText("Location: " + event.Location);
        holder.dateView.setText("Date: " + event.Date);
        holder.typeView.setText("Type: " + event.Type);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventNameView;
        TextView descriptionView;
        TextView maxParticipantsView;
        TextView privacyView;
        TextView locationView;
        TextView dateView;
        TextView typeView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventNameView = itemView.findViewById(R.id.eventNameView);
            descriptionView = itemView.findViewById(R.id.descriptionView);
            maxParticipantsView = itemView.findViewById(R.id.maxParticipantsView);
            privacyView = itemView.findViewById(R.id.privacyView);
            locationView = itemView.findViewById(R.id.locationView);
            dateView = itemView.findViewById(R.id.dateView);
            typeView = itemView.findViewById(R.id.typeView);
        }
    }
}
