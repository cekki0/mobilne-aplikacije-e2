package com.example.fijiapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.model.Event;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private List<Event> events;
    private Context context;

    public EventAdapter(List<Event> dataSet, Context context) {
        this.events = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        return new EventAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {
        Event event = events.get(position);
        holder.eventNameView.setText("Name: " + event.EventName);
        holder.dateView.setText("Date: " + event.Date);
        holder.startTimeView.setText("StartTime: " + event.StartTime);
        holder.endTimeView.setText("EndTime: " + event.EndTime);
        holder.typeView.setText("Type: " + event.Type);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventNameView;
        TextView dateView;
        TextView startTimeView;
        TextView endTimeView;
        TextView typeView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventNameView = itemView.findViewById(R.id.eventNameView);
            dateView = itemView.findViewById(R.id.dateView);
            startTimeView = itemView.findViewById(R.id.startTimeView);
            endTimeView = itemView.findViewById(R.id.endTimeView);
            typeView = itemView.findViewById(R.id.typeView);
        }
    }
}