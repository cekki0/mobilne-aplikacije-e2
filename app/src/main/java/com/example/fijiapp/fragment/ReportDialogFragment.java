package com.example.fijiapp.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.fijiapp.R;

public class ReportDialogFragment extends DialogFragment {
    public interface ReportDialogListener {
        void onReportPositiveClick(String reason);
    }

    private ReportDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Report")
                .setView(R.layout.report_dialog)
                .setPositiveButton("Report", (dialog, which) -> {
                    if (listener != null) {
                        String reason = getReasonFromDialog();
                        listener.onReportPositiveClick(reason);
                    }
                })
                .setNegativeButton("Cancel", null);
        return builder.create();
    }

    private String getReasonFromDialog() {
        return ((EditText) getDialog().findViewById(R.id.edit_text_reason)).getText().toString();
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        if (listener != null) {
            listener.onReportPositiveClick("");
        }
    }

    public void setReportDialogListener(ReportDialogListener listener) {
        this.listener = listener;
    }
}
