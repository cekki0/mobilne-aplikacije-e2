package com.example.fijiapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fijiapp.model.Service;

public class UpdateServiceActivity extends AppCompatActivity {
    private EditText nameEditText, descriptionEditText, categoryEditText, subCategoryEditText,
            priceEditText, totalPriceEditText, durationEditText, locationEditText,
            discountEditText, specificsEditText, bookingDeadlineEditText,
            cancellationDeadlineEditText, acceptanceModeEditText;
    private Button saveButton;
    private CheckBox availableEditText, visibleEditText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_service);

        nameEditText = findViewById(R.id.nameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        categoryEditText = findViewById(R.id.categoryEditText);
        //subCategoryEditText = findViewById(R.id.subCategoryEditText);
        priceEditText = findViewById(R.id.priceEditText);
        //totalPriceEditText = findViewById(R.id.totalPriceEditText);
        durationEditText = findViewById(R.id.durationEditText);
        locationEditText = findViewById(R.id.locationEditText);
        discountEditText = findViewById(R.id.discountEditText);
        specificsEditText = findViewById(R.id.specificsEditText);
        bookingDeadlineEditText = findViewById(R.id.bookingDeadlineEditText);
        cancellationDeadlineEditText = findViewById(R.id.cancellationDeadlineEditText);
        acceptanceModeEditText = findViewById(R.id.acceptanceModeEditText);
        availableEditText = findViewById(R.id.availableCheckBox);
        visibleEditText = findViewById(R.id.visibleCheckBox);
        saveButton = findViewById(R.id.saveButton);

        Service service = getIntent().getParcelableExtra("service");
        nameEditText.setText(service.getName());
        descriptionEditText.setText(service.getDescription());
        categoryEditText.setText(service.getCategory());
        priceEditText.setText(String.valueOf(service.getPricePerHour()));
        durationEditText.setText(String.valueOf(service.getDurationHours()));

        locationEditText.setText(service.getLocation());
        discountEditText.setText(String.valueOf(service.getDiscount()));
        specificsEditText.setText(service.getSpecifics());
        bookingDeadlineEditText.setText(service.getBookingDeadline());
        cancellationDeadlineEditText.setText(service.getCancellationDeadline());
        acceptanceModeEditText.setText(service.getAcceptanceMode());
        availableEditText.setChecked(service.getAvailable().equals("Da"));
        visibleEditText.setChecked(service.getVisible().equals("Da"));



    }
}
