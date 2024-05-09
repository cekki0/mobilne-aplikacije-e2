package com.example.fijiapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fijiapp.R;
import com.example.fijiapp.model.Service;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateServiceActivity extends AppCompatActivity {
    private EditText nameEditText, descriptionEditText, subCategoryEditText,
            priceEditText, durationEditText, locationEditText,
            discountEditText, specificsEditText, bookingDeadlineEditText,
            cancellationDeadlineEditText, acceptanceModeEditText;
    private CheckBox availableCheckBox, visibleCheckBox;
    private Button saveButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_service);

        db = FirebaseFirestore.getInstance();

        nameEditText = findViewById(R.id.nameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        subCategoryEditText = findViewById(R.id.subCategoryEditText);
        priceEditText = findViewById(R.id.priceEditText);
        durationEditText = findViewById(R.id.durationEditText);
        locationEditText = findViewById(R.id.locationEditText);
        discountEditText = findViewById(R.id.discountEditText);
        specificsEditText = findViewById(R.id.specificsEditText);
        bookingDeadlineEditText = findViewById(R.id.bookingDeadlineEditText);
        cancellationDeadlineEditText = findViewById(R.id.cancellationDeadlineEditText);
        acceptanceModeEditText = findViewById(R.id.acceptanceModeEditText);
        availableCheckBox = findViewById(R.id.availableCheckBox);
        visibleCheckBox = findViewById(R.id.visibleCheckBox);
        saveButton = findViewById(R.id.saveButton);

        Service service = getIntent().getParcelableExtra("service");
        if (service != null) {
            nameEditText.setText(service.getName());
            descriptionEditText.setText(service.getDescription());
            subCategoryEditText.setText(service.getSubCategory());
            priceEditText.setText(String.valueOf(service.getPricePerHour()));
            durationEditText.setText(String.valueOf(service.getDurationHours()));
            locationEditText.setText(service.getLocation());
            discountEditText.setText(String.valueOf(service.getDiscount()));
            specificsEditText.setText(service.getSpecifics());
            bookingDeadlineEditText.setText(service.getBookingDeadline());
            cancellationDeadlineEditText.setText(service.getCancellationDeadline());
            acceptanceModeEditText.setText(service.getAcceptanceMode());
            availableCheckBox.setChecked(service.getAvailable().equals("Da"));
            visibleCheckBox.setChecked(service.getVisible().equals("Da"));
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (service != null) {
                    service.setName(nameEditText.getText().toString());
                    service.setDescription(descriptionEditText.getText().toString());
                    service.setSubCategory(subCategoryEditText.getText().toString());
                    service.setPricePerHour((int) Double.parseDouble(priceEditText.getText().toString()));
                    service.setDurationHours(Integer.parseInt(durationEditText.getText().toString()));
                    service.setLocation(locationEditText.getText().toString());
                    service.setDiscount(Integer.parseInt(discountEditText.getText().toString()));
                    service.setSpecifics(specificsEditText.getText().toString());
                    service.setBookingDeadline(bookingDeadlineEditText.getText().toString());
                    service.setCancellationDeadline(cancellationDeadlineEditText.getText().toString());
                    service.setAcceptanceMode(acceptanceModeEditText.getText().toString());
                    service.setAvailable(availableCheckBox.isChecked() ? "Da" : "Ne");
                    service.setVisible(visibleCheckBox.isChecked() ? "Da" : "Ne");

                     db.collection("services")
                            .whereEqualTo("name", service.getName())
                            .get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                                     db.collection("services").document(documentSnapshot.getId())
                                            .update("name", nameEditText.getText().toString(),
                                                    "description", descriptionEditText.getText().toString())
                                            .addOnSuccessListener(aVoid -> {
                                                Toast.makeText(UpdateServiceActivity.this, "Service updated successfully!", Toast.LENGTH_SHORT).show();
                                                finish();
                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(UpdateServiceActivity.this, "Failed to update service.", Toast.LENGTH_SHORT).show();
                                                Log.e("UpdateServiceActivity", "Error updating service", e);
                                            });
                                }
                            })
                            .addOnFailureListener(e -> {

                                Toast.makeText(UpdateServiceActivity.this, "Error querying database: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("UpdateServiceActivity", "Error querying database", e);
                            });
                }
            }
        });
    }
}