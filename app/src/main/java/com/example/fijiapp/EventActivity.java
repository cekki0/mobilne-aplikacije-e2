package com.example.fijiapp;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        populateEventType();

        // Set up button click listener
        Button buttonManageServices = findViewById(R.id.buttonManageServices);
        buttonManageServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the ServiceDialog
                ServiceDialog serviceDialog = new ServiceDialog(EventActivity.this);
                serviceDialog.show();
            }
        });

        populateEventType();
    }

    private void populateEventType() {
        // Define event types
        String[] eventTypes = {"Type 1", "Type 2", "Type 3", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerEventType = findViewById(R.id.spinnerEventType);
        spinnerEventType.setAdapter(adapter);
    }
}