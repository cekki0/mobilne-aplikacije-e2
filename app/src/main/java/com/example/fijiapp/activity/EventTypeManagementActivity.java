package com.example.fijiapp.activity;

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

        initData();

        eventTypeAdapter = new EventTypeAdapter(eventTypeList, this);
        eventTypeRecyclerView.setAdapter(eventTypeAdapter);
    }

    private void initData() {
        eventTypeList = new ArrayList<>();

        List<SubCategory> suggestedSubCategories1 = new ArrayList<>();
        suggestedSubCategories1.add(new SubCategory("Catering", "Catering service", SubCategoryType.SERVICE));
        suggestedSubCategories1.add(new SubCategory("Floral", "Floral arrangement service", SubCategoryType.SERVICE));
        eventTypeList.add(new EventType("Wedding", "Wedding event type", suggestedSubCategories1));

        List<SubCategory> suggestedSubCategories2 = new ArrayList<>();
        suggestedSubCategories2.add(new SubCategory("Venue", "Venue service", SubCategoryType.SERVICE));
        suggestedSubCategories2.add(new SubCategory("Photography", "Photography service", SubCategoryType.SERVICE));
        eventTypeList.add(new EventType("Birthday", "Birthday event type", suggestedSubCategories2));

    }

    public void onAddEventTypeBtnClick(View view) {
        Intent intent = new Intent(this, EventTypeAddActivity.class);
        startActivity(intent);
    }

}
