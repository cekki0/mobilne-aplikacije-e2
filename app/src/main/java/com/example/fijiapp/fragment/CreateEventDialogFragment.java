package com.example.fijiapp.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;

import com.example.fijiapp.R;
import com.example.fijiapp.model.Event;
import com.example.fijiapp.model.EventTypes;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class CreateEventDialogFragment extends DialogFragment {

    private TextInputEditText etEventName;
    private TextInputEditText etStartTime;
    private TextInputEditText etEndTime;
    private CalendarView calendarView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_create_event_dialog, null);

        etEventName = view.findViewById(R.id.etEventName);
        etStartTime = view.findViewById(R.id.etStartTime);
        etEndTime = view.findViewById(R.id.etEndTime);
        calendarView = view.findViewById(R.id.calendarView);

        builder.setView(view)
                .setTitle("Fill Event information")
                .setPositiveButton("OK", (dialog, which) -> {
                    // Retrieve the entered event details
                    String eventName = etEventName.getText().toString();
                    String startTime = etStartTime.getText().toString();
                    String endTime = etEndTime.getText().toString();
                    long selectedDateMillis = calendarView.getDate();
                    // Convert selected date to Calendar object
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.setTimeInMillis(selectedDateMillis);
                    // Add logic to handle the entered event details
                    saveEventToFirestore(eventName, startTime, endTime, selectedDate.getTime());
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Handle cancel button click
                    dismiss();
                });

        return builder.create();
    }

    private void saveEventToFirestore(String eventName, String startTime, String endTime, Date date) {
        // Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference eventsRef = db.collection("events");

        // Create an Event object
        Event event = new Event(eventName, date, LocalTime.parse(startTime), LocalTime.parse(endTime), EventTypes.RESERVED);

        // Save the Event object to Firestore
        eventsRef.add(event)
                .addOnSuccessListener(documentReference -> Log.d("Firestore", "Event added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w("Firestore", "Error adding event", e));
    }
}