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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.Calendar;

public class CreateEventDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_create_event_dialog, null);


        // EditText editTextMondayStart = view.findViewById(R.id.editTextMondayStart);
        // EditText editTextMondayEnd = view.findViewById(R.id.editTextMondayEnd);
        // Add similar EditText fields for other days as needed

        builder.setView(view)
                .setTitle("Fill Event information")
                .setPositiveButton("OK", (dialog, which) -> {
                    // Retrieve the entered work hours
                    // String mondayStartTime = editTextMondayStart.getText().toString();
                    // String mondayEndTime = editTextMondayEnd.getText().toString();
                    // Add logic to handle the entered work hours
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Handle cancel button click
                    dismiss();
                });

        return builder.create();
    }
}