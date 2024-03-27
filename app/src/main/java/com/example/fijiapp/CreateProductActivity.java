package com.example.fijiapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CreateProductActivity extends AppCompatActivity {

    public CreateProductActivity(){}
    public CreateProductActivity(EditText category, EditText subCategory, EditText title, EditText description, EditText selectedImages, EditText eventTypes, EditText price, EditText discount, CheckBox visibleCheckBox, CheckBox availableCheckBox, Button createButton) {
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
    }

    public CreateProductActivity(int contentLayoutId, EditText category, EditText subCategory, EditText title, EditText description, EditText selectedImages, EditText eventTypes, EditText price, EditText discount, CheckBox visibleCheckBox, CheckBox availableCheckBox, Button createButton) {
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
    }

    private EditText category;
    private EditText subCategory;
    private EditText title;
    private EditText description;
    private EditText selectedImages;
    private EditText eventTypes ;
    private EditText price;
    private EditText discount;
    private CheckBox visibleCheckBox;
    private CheckBox availableCheckBox;
    private Button createButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

            title = findViewById(R.id.titleEditText);
            description = findViewById(R.id.descriptionEditText);
            category = findViewById(R.id.categoryEditText);
            subCategory = findViewById(R.id.subCategoryEditText);
            price = findViewById(R.id.priceEditText);
            discount = findViewById(R.id.discountEditText);
            visibleCheckBox = findViewById(R.id.visibleCheckBox);
            availableCheckBox = findViewById(R.id.availableCheckBox);
            createButton =  findViewById(R.id.createProductButton);
            selectedImages = findViewById(R.id.selectImagesButton);
            eventTypes = findViewById(R.id.eventTypesEditText);



    }
}
