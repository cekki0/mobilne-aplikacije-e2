package com.example.fijiapp;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fijiapp.fragment.WorkHoursDialogFragment;

public class StaffRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_registration);
    }

    public void onWorkHoursButtonClick(View view) {
        WorkHoursDialogFragment dialogFragment = new WorkHoursDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "WorkHoursDialogFragment");
    }
}