package com.example.fijiapp.activity;

import static com.example.fijiapp.model.UserRole.SERVICE_PROVIDER;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fijiapp.R;
import com.example.fijiapp.activity.register.ServiceProviderRegistrationActivity;
import com.example.fijiapp.fragment.WorkHoursDialogFragment;
import com.example.fijiapp.model.Company;
import com.example.fijiapp.model.User;
import com.example.fijiapp.model.UserRole;
import com.example.fijiapp.model.WorkDays;
import com.example.fijiapp.model.WorkHours;
import com.example.fijiapp.model.WorkingDay;
import com.example.fijiapp.service.OwnerService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffRegistrationActivity extends AppCompatActivity implements WorkHoursDialogFragment.WorkHoursDialogListener {

    private TextInputEditText editTextEmail, editTextPassword, editTextConfirmPassword,
            editTextFirstName, editTextLastName, editTextAddress, editTextPhoneNumber, editTextProfileImage;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<WorkingDay> workHours = new ArrayList<>();
    private User currentUser;
    private Company currentCompany;
    private FirebaseAuth mAuth;
    private OwnerService ownerService = new OwnerService();

    @Override
    public void onWorkHoursEntered(LocalTime mondayStartTime, LocalTime mondayEndTime, LocalTime tuesdayStartTime, LocalTime tuesdayEndTime, LocalTime wednesdayStartTime, LocalTime wednesdayEndTime, LocalTime thursdayStartTime, LocalTime thursdayEndTime, LocalTime fridayStartTime, LocalTime fridayEndTime) {
        workHours = new ArrayList<>();
        workHours.add(new WorkingDay(WorkDays.MON, new WorkHours(mondayStartTime, mondayEndTime)));
        workHours.add(new WorkingDay(WorkDays.TUE, new WorkHours(mondayStartTime, mondayEndTime)));
        workHours.add(new WorkingDay(WorkDays.WED, new WorkHours(mondayStartTime, mondayEndTime)));
        workHours.add(new WorkingDay(WorkDays.THU, new WorkHours(mondayStartTime, mondayEndTime)));
        workHours.add(new WorkingDay(WorkDays.FRI, new WorkHours(mondayStartTime, mondayEndTime)));
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

        MaterialButton buttonCreateUser = findViewById(R.id.buttonCreateUser);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUserAuth = mAuth.getCurrentUser();
        buttonCreateUser.setEnabled(false);
        buttonCreateUser.setOnClickListener(v -> registerStaff());

        assert currentUserAuth != null;
        String currentEmail = currentUserAuth.getEmail();
        ownerService.getUserByEmail(currentEmail)
                .addOnSuccessListener(extendedUser -> {
                    if (extendedUser != null) {
                        currentUser = extendedUser;
                        ownerService.getCompanyByOwnerEmail(currentUser.Email).addOnSuccessListener(company -> {
                                    if (company != null) {
                                        currentCompany = company;
                                        workHours = currentCompany.WorkingDays;
                                        buttonCreateUser.setEnabled(true);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Company data not found!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                });
                    } else {
                        Toast.makeText(getApplicationContext(), "User data not found!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
        staff.put("Email", email);
        staff.put("FirstName", firstName);
        staff.put("LastName", lastName);
        staff.put("Address", address);
        staff.put("PhoneNumber", phoneNumber);
        staff.put("ProfileImage", profileImage);
        staff.put("Role", UserRole.STAFF);
        staff.put("Company", currentCompany.Email);
        staff.put("WorkingDays", workHours);

        db.collection("users")
                .document(staff.get("Email").toString())
                .set(staff, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(StaffRegistrationActivity.this, "Staff registered successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(StaffRegistrationActivity.this, e.toString(), Toast.LENGTH_SHORT).show());

//        mAuth.getInstance().createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, task -> {
//                    if (task.isSuccessful()) {
//                        FirebaseUser user = task.getResult().getUser();
//                        if (user != null) {
//                            user.sendEmailVerification()
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()) {
//                                                Toast.makeText(StaffRegistrationActivity.this, "Verification email sent", Toast.LENGTH_SHORT).show();
//                                            } else {
//                                                Toast.makeText(StaffRegistrationActivity.this, "Failed to send verification email", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    });
//                        }
//
//                    } else {
//                        Toast.makeText(StaffRegistrationActivity.this, "Error occurred!",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
    }


}