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

    private Map<String, List<String>> service;
    private List<Map<String, String>> services;
    private Map<String, String> selectedService;
    Spinner spinnerServiceSubtype;
    Spinner spinnerServiceType;
    String selectedServiceSubtype;
    String selectedServiceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);


        populateEventType();
        // Initialize the HashMap
        service = new HashMap<>();
        services = new ArrayList<>();
        selectedService = new HashMap<>();

        populateServiceSubtypeMap();
        populateSpinners();

        Button buttonAddSpinners = findViewById(R.id.buttonAddSpinners);
        ListView listView = findViewById(R.id.listView);

        final ArrayAdapter<Map<String, String>> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, services);
        // Set the adapter to the ListView
        listView.setAdapter(adapter);

        buttonAddSpinners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedServiceType = spinnerServiceType.getSelectedItem().toString();
                selectedServiceSubtype = spinnerServiceSubtype.getSelectedItem().toString();

                selectedService.put(selectedServiceType, selectedServiceSubtype);
                services.add(new HashMap<>(selectedService)); // Add a copy of the selectedService
                selectedService.clear();

                // Notify the adapter that the data set has changed
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void populateEventType() {
        // Define event types
        String[] eventTypes = {"Type 1", "Type 2", "Type 3", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerEventType = findViewById(R.id.spinnerEventType);
        spinnerEventType.setAdapter(adapter);
    }

    private void populateServiceSubtypeMap() {
        // For example purposes, let's populate the HashMap with some sample data
        List<String> subtypeList1 = new ArrayList<>();
        subtypeList1.add("Subtype A1");
        subtypeList1.add("Subtype A2");
        subtypeList1.add("Subtype A3");
        service.put("Service Type A", subtypeList1);

        List<String> subtypeList2 = new ArrayList<>();
        subtypeList2.add("Subtype B1");
        subtypeList2.add("Subtype B2");
        subtypeList2.add("Subtype B3");
        service.put("Service Type B", subtypeList2);
    }

    private void populateSpinners() {
        // Get reference to the Spinners
        spinnerServiceType = findViewById(R.id.spinnerServiceType);
        spinnerServiceSubtype = findViewById(R.id.spinnerServiceSubtype);

        // Create an ArrayAdapter for service types and set it to the Spinner
        ArrayAdapter<String> serviceTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>(service.keySet()));
        spinnerServiceType.setAdapter(serviceTypeAdapter);

        // Set a listener to the service type Spinner to update the subtype Spinner when a type is selected
        spinnerServiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected service type
                String selectedType = (String) parent.getItemAtPosition(position);

                // Get the corresponding subtypes from the HashMap
                List<String> subtypes = service.get(selectedType);

                // Create an ArrayAdapter for subtypes and set it to the subtype Spinner
                ArrayAdapter<String> serviceSubtypeAdapter = new ArrayAdapter<>(EventActivity.this, android.R.layout.simple_spinner_item, subtypes);
                spinnerServiceSubtype.setAdapter(serviceSubtypeAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
}