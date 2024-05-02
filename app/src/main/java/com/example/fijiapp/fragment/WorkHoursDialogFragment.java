package com.example.fijiapp.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.fijiapp.R;

import java.time.LocalTime;

public class WorkHoursDialogFragment extends DialogFragment {

    public interface WorkHoursDialogListener {
        void onWorkHoursEntered(LocalTime mondayStartTime, LocalTime mondayEndTime, LocalTime tuesdayStartTime, LocalTime tuesdayEndTime, LocalTime wednesdayStartTime, LocalTime wednesdayEndTime, LocalTime thursdayStartTime, LocalTime thursdayEndTime, LocalTime fridayStartTime, LocalTime fridayEndTime);
    }

    private WorkHoursDialogListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (WorkHoursDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement WorkHoursDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_work_hours, null);

        EditText editTextMondayStart = view.findViewById(R.id.editTextMondayStart);
        EditText editTextMondayEnd = view.findViewById(R.id.editTextMondayEnd);
        EditText editTextTuesdayStart = view.findViewById(R.id.editTextTuesdayStart);
        EditText editTextTuesdayEnd = view.findViewById(R.id.editTextTuesdayEnd);
        EditText editTextWednesdayStart = view.findViewById(R.id.editTextWednesdayStart);
        EditText editTextWednesdayEnd = view.findViewById(R.id.editTextWednesdayEnd);
        EditText editTextThursdayStart = view.findViewById(R.id.editTextThursdayStart);
        EditText editTextThursdayEnd = view.findViewById(R.id.editTextThursdayEnd);
        EditText editTextFridayStart = view.findViewById(R.id.editTextFridayStart);
        EditText editTextFridayEnd = view.findViewById(R.id.editTextFridayEnd);

        builder.setView(view)
                .setTitle("Fill Work Hours")
                .setPositiveButton("OK", (dialog, which) -> {
                    try {

                        LocalTime mondayStartTime = LocalTime.parse(editTextMondayStart.getText().toString());
                        LocalTime mondayEndTime = LocalTime.parse(editTextMondayEnd.getText().toString());

                        LocalTime tuesdayStartTime = LocalTime.parse(editTextTuesdayStart.getText().toString());
                        LocalTime tuesdayEndTime = LocalTime.parse(editTextTuesdayEnd.getText().toString());

                        LocalTime wednesdayStartTime = LocalTime.parse(editTextWednesdayStart.getText().toString());
                        LocalTime wednesdayEndTime = LocalTime.parse(editTextWednesdayEnd.getText().toString());

                        LocalTime thursdayStartTime = LocalTime.parse(editTextThursdayStart.getText().toString());
                        LocalTime thursdayEndTime = LocalTime.parse(editTextThursdayEnd.getText().toString());

                        LocalTime fridayStartTime = LocalTime.parse(editTextFridayStart.getText().toString());
                        LocalTime fridayEndTime = LocalTime.parse(editTextFridayEnd.getText().toString());


                        mListener.onWorkHoursEntered(mondayStartTime, mondayEndTime, tuesdayStartTime, tuesdayEndTime, wednesdayStartTime, wednesdayEndTime, thursdayStartTime, thursdayEndTime, fridayStartTime, fridayEndTime);
                    } catch (Exception e) {
                        Log.e("Error", e.toString());
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dismiss();
                });

        return builder.create();
    }
}
