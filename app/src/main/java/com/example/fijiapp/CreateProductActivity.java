package com.example.fijiapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class CreateProductActivity extends AppCompatActivity {

    public CreateProductActivity(){}
    public CreateProductActivity(Spinner category, Spinner subCategory, EditText title, EditText description, EditText selectedImages, EditText eventTypes, EditText price, EditText discount, CheckBox visibleCheckBox, CheckBox availableCheckBox, Button createButton, EditText customSubcategoryEditText) {
        this.category = category;
        this.subCategory = subCategory;
        this.title = title;
        this.description = description;
        this.selectedImages = selectedImages;
        this.eventTypes = eventTypes;
        this.price = price;
        this.discount = discount;
        this.visibleCheckBox = visibleCheckBox;
        this.availableCheckBox = availableCheckBox;
        this.createButton = createButton;
        this.customSubcategoryEditText = customSubcategoryEditText;
    }

    public CreateProductActivity(int contentLayoutId, Spinner category, Spinner subCategory, EditText title, EditText description, EditText selectedImages, EditText eventTypes, EditText price, EditText discount, CheckBox visibleCheckBox, CheckBox availableCheckBox, Button createButton, EditText customSubcategoryEditText) {
        super(contentLayoutId);
        this.category = category;
        this.subCategory = subCategory;
        this.title = title;
        this.description = description;
        this.selectedImages = selectedImages;
        this.eventTypes = eventTypes;
        this.price = price;
        this.discount = discount;
        this.visibleCheckBox = visibleCheckBox;
        this.availableCheckBox = availableCheckBox;
        this.createButton = createButton;
        this.customSubcategoryEditText = customSubcategoryEditText;
    }

    private Spinner category;
    private Spinner subCategory;
    private EditText title;
    private EditText description;
    private EditText selectedImages;
    private EditText eventTypes ;
    private EditText price;
    private EditText discount;
    private CheckBox visibleCheckBox;
    private CheckBox availableCheckBox;
    private Button createButton;
    private EditText customSubcategoryEditText;
    Map<String, List<String>> categorySubcategoryMap = new HashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        List<String> fotoSubcategories = Arrays.asList("Fotografije i Albumi", "Radosnice", "Maturanti","Custom");
        List<String> laptopSubcategories = Arrays.asList("Dell", "HP", "Lenovo","Custom");

        categorySubcategoryMap.put("Foto i Video", fotoSubcategories);
        categorySubcategoryMap.put("Laptops", laptopSubcategories);



        setContentView(R.layout.activity_create_product);

            title = findViewById(R.id.titleEditText);
            description = findViewById(R.id.descriptionEditText);
            category = findViewById(R.id.categorySpinner);
            subCategory = findViewById(R.id.subcategorySpinner);
            price = findViewById(R.id.priceEditText);
            discount = findViewById(R.id.discountEditText);
            visibleCheckBox = findViewById(R.id.visibleCheckBox);
            availableCheckBox = findViewById(R.id.availableCheckBox);
            createButton =  findViewById(R.id.createProductButton);
            selectedImages = findViewById(R.id.selectImagesButton);
            eventTypes = findViewById(R.id.eventTypesEditText);
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
                ArrayAdapter<String> subcategoryAdapter = new ArrayAdapter<>(CreateProductActivity.this, android.R.layout.simple_spinner_item, subcategories);

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

    }

}
