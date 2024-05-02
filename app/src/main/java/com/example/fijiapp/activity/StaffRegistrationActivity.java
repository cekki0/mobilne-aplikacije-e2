package com.example.fijiapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fijiapp.R;
import com.example.fijiapp.fragment.WorkHoursDialogFragment;
import com.example.fijiapp.model.UserRole;
import com.example.fijiapp.model.WorkDays;
import com.example.fijiapp.model.WorkHours;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class StaffRegistrationActivity extends AppCompatActivity implements WorkHoursDialogFragment.WorkHoursDialogListener {

    private TextInputEditText editTextEmail, editTextPassword, editTextConfirmPassword,
            editTextFirstName, editTextLastName, editTextAddress, editTextPhoneNumber, editTextProfileImage;
    private MaterialButton buttonCreateUser;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Map<WorkDays, com.example.fijiapp.model.WorkHours> workHours;

    @Override
    public void onWorkHoursEntered(LocalTime mondayStartTime, LocalTime mondayEndTime, LocalTime tuesdayStartTime, LocalTime tuesdayEndTime, LocalTime wednesdayStartTime, LocalTime wednesdayEndTime, LocalTime thursdayStartTime, LocalTime thursdayEndTime, LocalTime fridayStartTime, LocalTime fridayEndTime) {
        workHours.put(WorkDays.MON, new WorkHours(mondayStartTime, mondayEndTime));
        workHours.put(WorkDays.TUE, new WorkHours(mondayStartTime, mondayEndTime));
        workHours.put(WorkDays.WED, new WorkHours(mondayStartTime, mondayEndTime));
        workHours.put(WorkDays.THU, new WorkHours(mondayStartTime, mondayEndTime));
        workHours.put(WorkDays.FRI, new WorkHours(mondayStartTime, mondayEndTime));
        Toast.makeText(StaffRegistrationActivity.this, "Saved work hours successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_registration);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextProfileImage = findViewById(R.id.editTextProfileImage);

        buttonCreateUser = findViewById(R.id.buttonCreateUser);

        buttonCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerStaff();
            }
        });
    }

    public void onWorkHoursButtonClick(View view) {
        WorkHoursDialogFragment dialogFragment = new WorkHoursDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "WorkHoursDialogFragment");
    }

    private void registerStaff() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String profileImage = editTextProfileImage.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (confirmPassword.isEmpty()) {
            editTextConfirmPassword.setError("Confirm Password is required");
            editTextConfirmPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            editTextConfirmPassword.setError("Passwords do not match");
            editTextConfirmPassword.requestFocus();
            return;
        }


        Map<String, Object> staff = new HashMap<>();
        staff.put("email", email);
        staff.put("firstName", firstName);
        staff.put("lastName", lastName);
        staff.put("address", address);
        staff.put("phoneNumber", phoneNumber);
        staff.put("profileImage", profileImage);
        staff.put("role", UserRole.STAFF);
        staff.put("company", 1); // TODO: Add current users company and work hours
        staff.put("workHours", workHours);

        db.collection("staff")
                .document(staff.get("email").toString())
                .set(staff, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(StaffRegistrationActivity.this, "Staff registered successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(StaffRegistrationActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                });
    }
}