package com.example.fijiapp.activity.event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.adapters.EventTypeAdapter;
import com.example.fijiapp.model.EventType;
import com.example.fijiapp.model.SubCategory;
import com.example.fijiapp.model.SubCategoryType;

import java.util.ArrayList;
import java.util.List;

public class EventTypeManagementActivity extends AppCompatActivity {
    private List<EventType> eventTypeList;
    private EventTypeAdapter eventTypeAdapter;
    private RecyclerView eventTypeRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_type_management);

        eventTypeRecyclerView = findViewById(R.id.eventTypeRecyclerView);
        eventTypeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventTypeAdapter = new EventTypeAdapter(eventTypeList, this);
        eventTypeRecyclerView.setAdapter(eventTypeAdapter);
    }

    public void onAddEventTypeBtnClick(View view) {
        Intent intent = new Intent(this, EventTypeAddActivity.class);
        startActivity(intent);
    }
}
