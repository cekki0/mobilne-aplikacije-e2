package com.example.fijiapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.adapters.WorkingDaysAdapter;
import com.example.fijiapp.model.WorkDays;
import com.example.fijiapp.model.WorkHours;
import com.example.fijiapp.model.WorkingDay;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceProviderRegistrationActivity extends AppCompatActivity {

    private List<WorkingDay> workingDayList;
    private EditText editTextEmail, editTextPassword, editTextConfirmPassword, editTextFirstName,
            editTextLastName, editTextAddress, editTextPhoneNumber,
            editTextCompanyEmail, editTextCompanyName, editTextCompanyAddress,
            editTextCompanyPhoneNumber, editTextCompanyAbout;

    private Spinner spinnerServiceCategories, spinnerServiceEvents, spinnerServiceWorkDays, spinnerStartTime, spinnerEndTime;
    private Button buttonRegister, buttonUploadProfilePicture, buttonUploadCompanyPictures,
            buttonServiceCategoryAdd, buttonServiceEventAdd, buttonServiceWorkDayAdd;

    private CheckBox checkBoxWorkingDay;

    private RecyclerView recyclerViewWorkingDays;
    private Boolean isBoxChecked = true;
    private WorkingDaysAdapter workingDaysAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_registration);
        workingDayList = new ArrayList<>();

        //Personal info
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);

        //Company info
        editTextCompanyEmail = findViewById(R.id.editTextCompanyEmail);
        editTextCompanyName = findViewById(R.id.editTextCompanyName);
        editTextCompanyAddress = findViewById(R.id.editTextCompanyAddress);
        editTextCompanyPhoneNumber = findViewById(R.id.editTextCompanyPhoneNumber);
        editTextCompanyAbout = findViewById(R.id.editTextCompanyAbout);

        //Buttons
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonUploadProfilePicture = findViewById(R.id.buttonUploadProfilePicture);
        buttonUploadCompanyPictures = findViewById(R.id.buttonUploadCompanyPictures);
        buttonServiceCategoryAdd = findViewById(R.id.buttonServiceCategoryAdd);
        buttonServiceEventAdd = findViewById(R.id.buttonServiceEventAdd);
        buttonServiceWorkDayAdd = findViewById(R.id.buttonServiceWorkDayAdd);

        spinnerServiceCategories = findViewById(R.id.spinnerServiceCategories);
        spinnerServiceEvents = findViewById(R.id.spinnerServiceEvents);
        spinnerServiceWorkDays = findViewById(R.id.spinnerServiceWorkDays);
        spinnerStartTime = findViewById(R.id.spinnerStartTime);
        spinnerEndTime = findViewById(R.id.spinnerEndTime);

        checkBoxWorkingDay = findViewById(R.id.checkBoxWorkingDay);

        recyclerViewWorkingDays = findViewById(R.id.recyclerViewWorkingDays);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewWorkingDays.setLayoutManager(layoutManager);
        workingDaysAdapter = new WorkingDaysAdapter(workingDayList);
        recyclerViewWorkingDays.setAdapter(workingDaysAdapter);

        String[] serviceCategories = {"Category 1", "Category 2", "Category 3"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, serviceCategories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServiceCategories.setAdapter(categoryAdapter);

        String[] serviceEvents = {"Event 1", "Event 2", "Event 3"};
        ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, serviceEvents);
        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServiceEvents.setAdapter(eventAdapter);

        String[] serviceWorkDays = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
        ArrayAdapter<String> workDaysAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, serviceWorkDays);
        workDaysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServiceWorkDays.setAdapter(workDaysAdapter);

        String[] timeSlots = {"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"};
        ArrayAdapter<String> startTimeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeSlots);
        startTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStartTime.setAdapter(startTimeAdapter);
        ArrayAdapter<String> endTimeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeSlots);
        endTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEndTime.setAdapter(endTimeAdapter);

        checkBoxWorkingDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isBoxChecked = true;
                    findViewById(R.id.timeSlotsLayout).setVisibility(View.VISIBLE);
                } else {
                    isBoxChecked = false;
                    findViewById(R.id.timeSlotsLayout).setVisibility(View.GONE);
                }
            }
        });

        buttonServiceWorkDayAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWorkingDay();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void addWorkingDay() {
        String selectedDay = spinnerServiceWorkDays.getSelectedItem().toString();
        String startTime = spinnerStartTime.getSelectedItem().toString();
        String endTime = spinnerEndTime.getSelectedItem().toString();

        boolean dayExists = false;
        for (WorkingDay day : workingDayList) {
            if (day.workDay.toString().equalsIgnoreCase(selectedDay)) {
                dayExists = true;
                break;
            }
        }

        if (!dayExists) {
            try {
                WorkingDay workingDay;
                if (isBoxChecked) {
                    LocalTime start = LocalTime.parse(startTime);
                    LocalTime end = LocalTime.parse(endTime);
                    WorkHours workHours = new WorkHours(start, end);
                    workingDay = new WorkingDay(WorkDays.valueOf(selectedDay.toUpperCase()), workHours);
                } else {
                    workingDay = new WorkingDay(WorkDays.valueOf(selectedDay.toUpperCase()), null);
                }
                workingDayList.add(workingDay);
                if (isBoxChecked)
                    Toast.makeText(this, "Added " + selectedDay + " with start time: " + startTime + " and end time: " + endTime, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Added " + selectedDay + " as non-working day ", Toast.LENGTH_SHORT).show();

                workingDaysAdapter.notifyDataSetChanged();
            } catch (IllegalArgumentException e) {
                Toast.makeText(this, "Invalid time range: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, selectedDay + " has already been added", Toast.LENGTH_SHORT).show();
        }
    }



    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String companyEmail = editTextCompanyEmail.getText().toString().trim();
        String companyName = editTextCompanyName.getText().toString().trim();
        String companyAddress = editTextCompanyAddress.getText().toString().trim();
        String companyPhoneNumber = editTextCompanyPhoneNumber.getText().toString().trim();
        String companyAbout = editTextCompanyAbout.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(getApplicationContext(), "Enter valid email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "Confirm your password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(getApplicationContext(), "Enter first name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(getApplicationContext(), "Enter last name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(address)) {
            Toast.makeText(getApplicationContext(), "Enter address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(getApplicationContext(), "Enter phone number!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(companyEmail)) {
            Toast.makeText(getApplicationContext(), "Enter company email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(companyEmail)) {
            Toast.makeText(getApplicationContext(), "Enter valid company email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(companyName)) {
            Toast.makeText(getApplicationContext(), "Enter company name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(companyAddress)) {
            Toast.makeText(getApplicationContext(), "Enter company address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(companyPhoneNumber)) {
            Toast.makeText(getApplicationContext(), "Enter company phone number!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(companyAbout)) {
            Toast.makeText(getApplicationContext(), "Enter information about the company!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void navigateToLoginPage(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}