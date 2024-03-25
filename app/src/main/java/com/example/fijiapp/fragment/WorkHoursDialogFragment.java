package com.example.fijiapp.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.fijiapp.R;

public class WorkHoursDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_work_hours, null);

        EditText editTextMondayStart = view.findViewById(R.id.editTextMondayStart);
        EditText editTextMondayEnd = view.findViewById(R.id.editTextMondayEnd);
        // Add similar EditText fields for other days as needed

        builder.setView(view)
                .setTitle("Fill Work Hours")
                .setPositiveButton("OK", (dialog, which) -> {
                    // Retrieve the entered work hours
                    String mondayStartTime = editTextMondayStart.getText().toString();
                    String mondayEndTime = editTextMondayEnd.getText().toString();
                    // Add logic to handle the entered work hours
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Handle cancel button click
                    dismiss();
                });

        return builder.create();
    }
}
