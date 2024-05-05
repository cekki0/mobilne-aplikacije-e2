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
import com.example.fijiapp.activity.event.EventTypeManagementActivity;
import com.example.fijiapp.model.EventType;
import com.example.fijiapp.service.EventTypeService;

import java.util.List;

public class EventTypeAdapter extends RecyclerView.Adapter<EventTypeAdapter.EventTypeViewHolder>  {

    private List<EventType> eventTypes;
    private Context context;
    private EventTypeService eventTypeService = new EventTypeService();

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

        holder.eventTypeNameTextView.setText(eventType.Name);
        holder.eventTypeDescriptionTextView.setText(eventType.Description);

        if (eventType.isActive) {
            holder.deactivateEventTypeButton.setText("Deactivate");
        } else {
            holder.deactivateEventTypeButton.setText("Activate");
        }

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
                eventTypeService.deleteEventType(eventType);
                refreshPage();
            }
        });
    }

    public void refreshPage() {
        Intent intent = new Intent(context, EventTypeManagementActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if(eventTypes!=null)
            return eventTypes.size();
        return 0;
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
