package com.example.fijiapp.activity.event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.activity.category.CategoryManagementAdminActivity;
import com.example.fijiapp.adapters.CategoryAdapter;
import com.example.fijiapp.adapters.EventTypeAdapter;
import com.example.fijiapp.model.Category;
import com.example.fijiapp.model.EventType;
import com.example.fijiapp.model.SubCategory;
import com.example.fijiapp.model.SubCategoryType;
import com.example.fijiapp.service.CategoryService;
import com.example.fijiapp.service.EventTypeService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class EventTypeManagementActivity extends AppCompatActivity {
    private List<EventType> eventTypes;
    private EventTypeAdapter eventTypeAdapter;
    private RecyclerView eventTypeRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_type_management);

        fetchAndInitializeRecyclerView();

    }

    private void fetchAndInitializeRecyclerView() {
        EventTypeService eventTypeService = new EventTypeService();
        eventTypeService.getAllEventTypes()
                .addOnCompleteListener(new OnCompleteListener<List<EventType>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<EventType>> task) {
                        if (task.isSuccessful()) {
                            eventTypes = task.getResult();
                            initializeRecyclerView();
                        } else {
                            Toast.makeText(EventTypeManagementActivity.this,
                                    "Failed to fetch event types", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initializeRecyclerView() {
        eventTypeRecyclerView = findViewById(R.id.eventTypeRecyclerView);
        eventTypeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventTypeAdapter = new EventTypeAdapter(eventTypes, this);
        eventTypeRecyclerView.setAdapter(eventTypeAdapter);
    }

    public void onAddEventTypeBtnClick(View view) {
        Intent intent = new Intent(this, EventTypeAddActivity.class);
        startActivity(intent);
    }
}
