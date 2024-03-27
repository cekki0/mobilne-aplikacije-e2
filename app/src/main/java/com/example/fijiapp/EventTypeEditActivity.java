package com.example.fijiapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fijiapp.model.EventType;

public class EventTypeEditActivity extends AppCompatActivity {

    private EditText eventTypeDescriptionEditText;
    private Button saveChangesButton;

    private EventType eventType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_type_edit);

        eventType = (EventType) getIntent().getSerializableExtra("eventType");

        eventTypeDescriptionEditText = findViewById(R.id.eventTypeDescriptionEditText);
        saveChangesButton = findViewById(R.id.saveChangesButton);

        eventTypeDescriptionEditText.setText(eventType.description);

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
    }

    private void saveChanges() {
        eventType.description = eventTypeDescriptionEditText.getText().toString().trim();
        Toast.makeText(this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
