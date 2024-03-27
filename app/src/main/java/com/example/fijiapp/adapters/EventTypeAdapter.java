package com.example.fijiapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.model.EventType;
import com.example.fijiapp.model.SubCategory;

import java.util.List;

public class EventTypeAdapter extends RecyclerView.Adapter<EventTypeAdapter.EventTypeViewHolder> {
    private List<EventType> eventTypeList;

    public EventTypeAdapter(List<EventType> eventTypeList) {
        this.eventTypeList = eventTypeList;
    }

    @NonNull
    @Override
    public EventTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_type_card_item, parent, false);
        return new EventTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventTypeViewHolder holder, int position) {
        EventType eventType = eventTypeList.get(position);
        holder.bind(eventType);
    }

    @Override
    public int getItemCount() {
        return eventTypeList.size();
    }

    public static class EventTypeViewHolder extends RecyclerView.ViewHolder {
        private TextView eventTypeNameTextView;
        private TextView eventTypeDescriptionTextView;
        private TextView eventTypeSubCategoriesTextView;

        public EventTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTypeNameTextView = itemView.findViewById(R.id.eventTypeNameTextView);
            eventTypeDescriptionTextView = itemView.findViewById(R.id.eventTypeDescriptionTextView);
            eventTypeSubCategoriesTextView = itemView.findViewById(R.id.eventTypeSubCategoriesTextView);
        }

        public void bind(EventType eventType) {
            eventTypeNameTextView.setText(eventType.name);
            eventTypeDescriptionTextView.setText(eventType.description);
            eventTypeSubCategoriesTextView.setText(formatSubCategories(eventType.suggestedSubCategories));
        }

        private String formatSubCategories(List<SubCategory> subCategories) {
            StringBuilder stringBuilder = new StringBuilder();
            for (SubCategory subCategory : subCategories) {
                stringBuilder.append(subCategory.Name).append(", ");
            }
            if (stringBuilder.length() > 0) {
                stringBuilder.setLength(stringBuilder.length() - 2); // Remove trailing comma and space
            }
            return stringBuilder.toString();
        }
    }
}
