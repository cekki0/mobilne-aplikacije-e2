package com.example.fijiapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fijiapp.R;
import com.example.fijiapp.model.Event;
import com.example.fijiapp.model.ODEvent;
import com.example.fijiapp.model.Privacy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventActivity extends AppCompatActivity implements ServiceDialog.OnSaveListener{

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<String> services = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        populateEventType();

        Button buttonManageServices = findViewById(R.id.buttonManageServices);
        buttonManageServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceDialog serviceDialog = new ServiceDialog(EventActivity.this, EventActivity.this);
                serviceDialog.show();
            }
        });

        Button buttonSaveEvent = findViewById(R.id.buttonSaveEvent);
        buttonSaveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEvent();
            }
        });

        populateEventType();
    }

    @Override
    public void onSave(List<String> services) {
        this.services = services;
    }

    private void populateEventType() {
        // Define event types
        String[] eventTypes = {"Type 1", "Type 2", "Type 3", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerEventType = findViewById(R.id.spinnerEventType);
        spinnerEventType.setAdapter(adapter);
    }

    private void saveEvent() {
        // Retrieve data from UI elements
        EditText editTextEventName = findViewById(R.id.editTextEventName);
        EditText editTextEventDescription = findViewById(R.id.editTextEventDescription);
        EditText editTextMaxParticipants = findViewById(R.id.editTextMaxParticipants);
        EditText editTextLocationConstraint = findViewById(R.id.editTextLocationConstraint);
        EditText editTextTimeConstraint = findViewById(R.id.editTextTimeConstraint);
        Spinner spinnerEventType = findViewById(R.id.spinnerEventType);
        RadioGroup radioGroupPrivateRules = findViewById(R.id.radioGroupPrivateRules);

        // Create Event object
        String eventName = editTextEventName.getText().toString().trim();
        String eventDescription = editTextEventDescription.getText().toString().trim();
        int maxParticipants = Integer.parseInt(editTextMaxParticipants.getText().toString().trim());
        String locationConstraint = editTextLocationConstraint.getText().toString().trim();
        String eventType = spinnerEventType.getSelectedItem().toString().trim();
        String timeConstraintString = editTextTimeConstraint.getText().toString().trim();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        Date timeConstraint = null;
        try {
            timeConstraint = dateFormat.parse(timeConstraintString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Get selected privacy (Public or Private)
        RadioButton selectedRadioButton = findViewById(radioGroupPrivateRules.getCheckedRadioButtonId());
        Privacy privacy = (selectedRadioButton.getId() == R.id.radioButtonPublic) ? Privacy.PUBLIC : Privacy.PRIVATE;

        ODEvent event = new ODEvent(eventName, eventDescription, maxParticipants, privacy, locationConstraint, timeConstraint, eventType, services);

        db.collection("odEvents")
                .add(event)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(EventActivity.this, "Event saved successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EventActivity.this, "Error saving event: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
