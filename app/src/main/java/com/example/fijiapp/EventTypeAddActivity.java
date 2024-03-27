package com.example.fijiapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fijiapp.model.EventType;

public class EventTypeAddActivity extends AppCompatActivity {

    private EditText eventTypeNameEditText;
    private EditText eventTypeDescriptionEditText;
    private Button addEventTypeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_type_add);

        eventTypeNameEditText = findViewById(R.id.eventTypeNameEditText);
        eventTypeDescriptionEditText = findViewById(R.id.eventTypeDescriptionEditText);
        addEventTypeButton = findViewById(R.id.addEventTypeButton);

        addEventTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEventType();
            }
        });
    }

    private void addEventType() {
        String name = eventTypeNameEditText.getText().toString().trim();
        String description = eventTypeDescriptionEditText.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please enter both event type name and description", Toast.LENGTH_SHORT).show();
            return;
        }

        EventType eventType = new EventType(name, description, null);
    }
}
