package com.example.fijiapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;

public class ServiceProviderRegistrationActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword, editTextConfirmPassword, editTextFirstName,
            editTextLastName, editTextAddress, editTextPhoneNumber,
            editTextCompanyEmail, editTextCompanyName, editTextCompanyAddress,
            editTextCompanyPhoneNumber, editTextCompanyAbout;

    private Spinner spinnerServiceCategories, spinnerServiceEvents, spinnerServiceWorkDays;
    private Button buttonRegister, buttonUploadProfilePicture, buttonUploadCompanyPictures,
            buttonServiceCategoryAdd, buttonServiceEventAdd, buttonServiceWorkHoursAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_registration);

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
        buttonServiceWorkHoursAdd = findViewById(R.id.buttonServiceWorkHoursAdd);

        spinnerServiceCategories = findViewById(R.id.spinnerServiceCategories);
        spinnerServiceEvents = findViewById(R.id.spinnerServiceEvents);
        spinnerServiceWorkDays = findViewById(R.id.spinnerServiceWorkDays);


        String[] serviceCategories = {"Category 1", "Category 2", "Category 3"}; // Sample categories
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, serviceCategories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServiceCategories.setAdapter(categoryAdapter);

        String[] serviceEvents = {"Event 1", "Event 2", "Event 3"}; // Sample events
        ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, serviceEvents);
        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServiceEvents.setAdapter(eventAdapter);

        String[] serviceWorkDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}; // All days
        ArrayAdapter<String> workDaysAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, serviceWorkDays);
        workDaysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServiceWorkDays.setAdapter(workDaysAdapter);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
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