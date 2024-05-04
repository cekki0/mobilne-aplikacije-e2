package com.example.fijiapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.activity.event.EventTypeEditActivity;
import com.example.fijiapp.R;
import com.example.fijiapp.model.EventType;

import java.util.List;

public class EventTypeAdapter extends RecyclerView.Adapter<EventTypeAdapter.EventTypeViewHolder> {

    private List<EventType> eventTypes;
    private Context context;

    public EventTypeAdapter(List<EventType> eventTypes, Context context) {
        this.eventTypes = eventTypes;
        this.context = context;
    }

    @NonNull
    @Override
    public EventTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_type_card_item, parent, false);
        return new EventTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventTypeViewHolder holder, int position) {
        EventType eventType = eventTypes.get(position);

        holder.eventTypeNameTextView.setText(eventType.name);
        holder.eventTypeDescriptionTextView.setText(eventType.description);

        holder.editEventTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventTypeEditActivity.class);
                intent.putExtra("eventType", eventType);
                context.startActivity(intent);
            }
        });

        holder.deactivateEventTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return eventTypes.size();
    }

    public static class EventTypeViewHolder extends RecyclerView.ViewHolder {
        TextView eventTypeNameTextView;
        TextView eventTypeDescriptionTextView;
        Button editEventTypeButton;
        Button deactivateEventTypeButton;

        public EventTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTypeNameTextView = itemView.findViewById(R.id.eventTypeNameTextView);
            eventTypeDescriptionTextView = itemView.findViewById(R.id.eventTypeDescriptionTextView);
            editEventTypeButton = itemView.findViewById(R.id.editEventTypeButton);
            deactivateEventTypeButton = itemView.findViewById(R.id.deactivateEventTypeButton);
        }
    }
}
