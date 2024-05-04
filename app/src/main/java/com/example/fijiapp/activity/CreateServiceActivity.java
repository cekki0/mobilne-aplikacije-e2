package com.example.fijiapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fijiapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateServiceActivity extends AppCompatActivity {

    private Spinner category;
    private Spinner subCategory;
    private EditText name;
    private EditText description;
    private EditText gallery;
    private EditText specifics;
    private EditText pricePerHour;

    private EditText durationHours;
    private EditText location;
    private EditText discount;
    private EditText serviceProviders;
    private EditText eventTypes;
    private EditText bookingDeadline;
    private EditText cancellationDeadline;
    private EditText acceptanceMode;
    private CheckBox available;
    private CheckBox visible;
    private EditText customSubcategoryEditText;
    Map<String, List<String>> categorySubcategoryMap = new HashMap<>();



    public CreateServiceActivity(){}

    public CreateServiceActivity(Spinner category, Spinner subCategory, EditText name, EditText description, EditText gallery, EditText specifics, EditText pricePerHour, EditText durationHours, EditText location, EditText discount, EditText serviceProviders, EditText eventTypes, EditText bookingDeadline, EditText cancellationDeadline, EditText acceptanceMode, CheckBox available, CheckBox visible, EditText customSubcategoryEditText) {
        this.category = category;
        this.subCategory = subCategory;
        this.name = name;
        this.description = description;
        this.gallery = gallery;
        this.specifics = specifics;
        this.pricePerHour = pricePerHour;
        this.durationHours = durationHours;
        this.location = location;
        this.discount = discount;
        this.serviceProviders = serviceProviders;
        this.eventTypes = eventTypes;
        this.bookingDeadline = bookingDeadline;
        this.cancellationDeadline = cancellationDeadline;
        this.acceptanceMode = acceptanceMode;
        this.available = available;
        this.visible = visible;
        this.customSubcategoryEditText = customSubcategoryEditText;
    }

    public CreateServiceActivity(int contentLayoutId, Spinner category, Spinner subCategory, EditText name, EditText description, EditText gallery, EditText specifics, EditText pricePerHour, EditText durationHours, EditText location, EditText discount, EditText serviceProviders, EditText eventTypes, EditText bookingDeadline, EditText cancellationDeadline, EditText acceptanceMode, CheckBox available, CheckBox visible, EditText customSubcategoryEditText) {
        super(contentLayoutId);
        this.category = category;
        this.subCategory = subCategory;
        this.name = name;
        this.description = description;
        this.gallery = gallery;
        this.specifics = specifics;
        this.pricePerHour = pricePerHour;
        this.durationHours = durationHours;
        this.location = location;
        this.discount = discount;
        this.serviceProviders = serviceProviders;
        this.eventTypes = eventTypes;
        this.bookingDeadline = bookingDeadline;
        this.cancellationDeadline = cancellationDeadline;
        this.acceptanceMode = acceptanceMode;
        this.available = available;
        this.visible = visible;

        this.customSubcategoryEditText = customSubcategoryEditText;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        List<String> fotoSubcategories = Arrays.asList("Fotografije i Albumi", "Radosnice", "Maturanti","Custom");
        List<String> laptopSubcategories = Arrays.asList("Dell", "HP", "Lenovo","Custom");

        categorySubcategoryMap.put("Foto i Video", fotoSubcategories);

        categorySubcategoryMap.put("Laptops", laptopSubcategories);
        setContentView(R.layout.activity_create_service);

        category = findViewById(R.id.categorySpinner);
        subCategory = findViewById(R.id.subcategorySpinner);
        name = findViewById(R.id.nameEditText);
        description = findViewById(R.id.descriptionEditText);
        gallery = findViewById(R.id.galleryEditText);
        specifics = findViewById(R.id.specificsEditText);
        pricePerHour = findViewById(R.id.pricePerHourEditText);
        durationHours = findViewById(R.id.durationHoursEditText);
        location = findViewById(R.id.locationEditText);
        discount = findViewById(R.id.discountEditText);
        serviceProviders = findViewById(R.id.serviceProvidersEditText);
        eventTypes = findViewById(R.id.eventTypesEditText);
        bookingDeadline = findViewById(R.id.bookingDeadlineEditText);
        cancellationDeadline = findViewById(R.id.cancellationDeadlineEditText);
        acceptanceMode = findViewById(R.id.acceptanceModeEditText);
        available = findViewById(R.id.availableCheckBox);
        visible = findViewById(R.id.visibleCheckBox);
        customSubcategoryEditText = findViewById(R.id.customSubcategoryEditText);


        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>(categorySubcategoryMap.keySet()));
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(categoryAdapter);


        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                Log.d("KATEGORIJA", "Selektovana kategorija: ");

                List<String> subcategories = categorySubcategoryMap.get(selectedCategory);
                ArrayAdapter<String> subcategoryAdapter = new ArrayAdapter<>(CreateServiceActivity.this, android.R.layout.simple_spinner_item, subcategories);

                subcategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subCategory.setAdapter(subcategoryAdapter);


                String selectedSubcategory = subCategory.getSelectedItem().toString();


                Log.d("Selected subcategory", selectedSubcategory);
                if (selectedSubcategory.equals("Custom") ) {
                    customSubcategoryEditText.setVisibility(View.VISIBLE);
                } else {
                    customSubcategoryEditText.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        subCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSubcategory = parent.getItemAtPosition(position).toString();

                if (selectedSubcategory.equals("Custom")) {
                    customSubcategoryEditText.setVisibility(View.VISIBLE);
                } else {
                    customSubcategoryEditText.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


}