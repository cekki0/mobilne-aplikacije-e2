package com.example.fijiapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.model.EventType;
import com.example.fijiapp.model.EventType;

import java.util.List;

public class EventTypeListAdapter extends RecyclerView.Adapter<EventTypeListAdapter.ViewHolder>{
    private List<EventType> eventTypes;

    public EventTypeListAdapter(List<EventType> eventTypes) {
        this.eventTypes = eventTypes;
    }

    @NonNull
    @Override
    public EventTypeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category, parent, false);
        return new EventTypeListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventTypeListAdapter.ViewHolder holder, int position) {
        EventType eventType = eventTypes.get(position);
        holder.bind(eventType);
    }

    @Override
    public int getItemCount() {
        if (!eventTypes.isEmpty())
            return eventTypes.size();
        else
            return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
        }

        public void bind(EventType eventType) {
            textName.setText(eventType.Name);
        }
    }
}
