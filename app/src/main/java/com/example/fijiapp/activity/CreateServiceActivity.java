package com.example.fijiapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fijiapp.R;
import com.example.fijiapp.model.Service;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateServiceActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    private Spinner categorySpinner;
    private Spinner subcategorySpinner;
    private EditText nameEditText;
    private EditText descriptionEditText;
    private EditText galleryEditText;
    private EditText specificsEditText;
    private EditText pricePerHourEditText;
    private EditText durationHoursEditText;
    private EditText locationEditText;
    private EditText discountEditText;
    private EditText serviceProvidersEditText;
    private EditText eventTypesEditText;
    private EditText bookingDeadlineEditText;
    private EditText cancellationDeadlineEditText;
    private EditText acceptanceModeEditText;
    private CheckBox availableCheckBox;
    private CheckBox visibleCheckBox;
    private EditText customSubcategoryEditText;
    private List<String> categoryList = new ArrayList<>();
    private List<String> subcategoryList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);

        db = FirebaseFirestore.getInstance();

        categorySpinner = findViewById(R.id.categorySpinner);
        subcategorySpinner = findViewById(R.id.subcategorySpinner);
        nameEditText = findViewById(R.id.nameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        galleryEditText = findViewById(R.id.galleryEditText);
        specificsEditText = findViewById(R.id.specificsEditText);
        pricePerHourEditText = findViewById(R.id.pricePerHourEditText);
        durationHoursEditText = findViewById(R.id.durationHoursEditText);
        locationEditText = findViewById(R.id.locationEditText);
        discountEditText = findViewById(R.id.discountEditText);
        serviceProvidersEditText = findViewById(R.id.serviceProvidersEditText);
        eventTypesEditText = findViewById(R.id.eventTypesEditText);
        bookingDeadlineEditText = findViewById(R.id.bookingDeadlineEditText);
        cancellationDeadlineEditText = findViewById(R.id.cancellationDeadlineEditText);
        acceptanceModeEditText = findViewById(R.id.acceptanceModeEditText);
        availableCheckBox = findViewById(R.id.availableCheckBox);
        visibleCheckBox = findViewById(R.id.visibleCheckBox);
        customSubcategoryEditText = findViewById(R.id.customSubcategoryEditText);

        loadCategoriesFromFirestore();
        loadSubcategoriesFromFirestore();

        findViewById(R.id.createServiceButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createService();
            }
        });
    }

    private void loadCategoriesFromFirestore() {
        db.collection("category")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    categoryList.clear();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        String categoryName = document.getString("Name");
                        categoryList.add(categoryName);
                    }
                    ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(CreateServiceActivity.this, android.R.layout.simple_spinner_item, categoryList);
                    categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categorySpinner.setAdapter(categoryAdapter);
                })
                .addOnFailureListener(e -> Log.e("CreateServiceActivity", "Error getting categories from Firestore", e));
    }

    private void loadSubcategoriesFromFirestore() {
        db.collection("subcategories")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    subcategoryList.clear();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        String subcategoryName = document.getString("Name");
                        subcategoryList.add(subcategoryName);
                    }
                    ArrayAdapter<String> subcategoryAdapter = new ArrayAdapter<>(CreateServiceActivity.this, android.R.layout.simple_spinner_item, subcategoryList);
                    subcategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subcategorySpinner.setAdapter(subcategoryAdapter);
                })
                .addOnFailureListener(e -> Log.e("CreateServiceActivity", "Error getting subcategories from Firestore", e));
    }

    private void createService() {
        String category = categorySpinner.getSelectedItem().toString();
        String subcategory = subcategorySpinner.getSelectedItem().toString();
        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String selectedImagesText = galleryEditText.getText().toString();
        String[] picturePaths = selectedImagesText.split(",");
        List<String> pictureList = Arrays.asList(picturePaths);
        String specifics = specificsEditText.getText().toString();
        int pricePerHour = Integer.parseInt(pricePerHourEditText.getText().toString());
        int durationHours = Integer.parseInt(durationHoursEditText.getText().toString());
        String location = locationEditText.getText().toString();
        int discount = Integer.parseInt(discountEditText.getText().toString());
        String serviceProvidersText = serviceProvidersEditText.getText().toString();
        String[] serviceProvidersArray = serviceProvidersText.split(",");
        List<String> serviceProviders = Arrays.asList(serviceProvidersArray);
        String eventTypesText = eventTypesEditText.getText().toString();
        String[] eventTypesArray = eventTypesText.split(",");
        List<String> eventTypes = Arrays.asList(eventTypesArray);
        String bookingDeadline = bookingDeadlineEditText.getText().toString();
        String cancellationDeadline = cancellationDeadlineEditText.getText().toString();
        String acceptanceMode = acceptanceModeEditText.getText().toString();
        String availableText = availableCheckBox.isChecked() ? "Da" : "Ne";
        String visibleText = visibleCheckBox.isChecked() ? "Da" : "Ne";
        String customSubcategory = customSubcategoryEditText.getText().toString();
        double totalPriceDouble = pricePerHour * durationHours * (1 - discount / 100.0);
        int totalPrice = (int) Math.round(totalPriceDouble);
        String status;
        if (!customSubcategory.isEmpty()) {
            status = "PENDING";
        } else {
            status = "APPROVAL";
        }
        Service service = new Service(category, subcategory, name, description, pictureList, specifics, pricePerHour, totalPrice, durationHours, location, discount, serviceProviders, eventTypes, bookingDeadline, cancellationDeadline, acceptanceMode, availableText, visibleText, status);

        db.collection("services")
                .add(service)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("CreateServiceActivity", "Service added with ID: " + documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("CreateServiceActivity", "Error adding service", e);

                    }
                });
    }
}
