package com.example.fijiapp.activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fijiapp.R;
import com.example.fijiapp.model.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class CreateProductActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    private Spinner categorySpinner;
    private Spinner subcategorySpinner;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText selectedImagesEditText;
    private EditText eventTypesEditText;
    private EditText priceEditText;
    private EditText discountEditText;
    private CheckBox visibleCheckBox;
    private CheckBox availableCheckBox;
    private Button createButton;
    private EditText customSubcategoryEditText;

    private List<String> categoryList = new ArrayList<>();
    private List<String> subcategoryList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        db = FirebaseFirestore.getInstance();

        categorySpinner = findViewById(R.id.categorySpinner);
        subcategorySpinner = findViewById(R.id.subcategorySpinner);
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        priceEditText = findViewById(R.id.priceEditText);
        discountEditText = findViewById(R.id.discountEditText);
        visibleCheckBox = findViewById(R.id.visibleCheckBox);
        availableCheckBox = findViewById(R.id.availableCheckBox);
        createButton = findViewById(R.id.createProductButton);
        selectedImagesEditText = findViewById(R.id.selectImagesButton);
        eventTypesEditText = findViewById(R.id.eventTypesEditText);
        customSubcategoryEditText = findViewById(R.id.customSubcategoryEditText);


        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        ArrayAdapter<String> subcategoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subcategoryList);
        subcategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subcategorySpinner.setAdapter(subcategoryAdapter);


        db.collection("category")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String categoryName = document.getString("Name");
                            categoryList.add(categoryName);
                        }
                        categoryAdapter.notifyDataSetChanged();
                    } else {
                        Log.w("CreateProductActivity", "Error getting documents.", task.getException());
                    }
                });


        db.collection("subcategories")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String subcategoryName = document.getString("Name");
                            subcategoryList.add(subcategoryName);
                        }
                        subcategoryAdapter.notifyDataSetChanged();
                    } else {
                        Log.w("CreateProductActivity", "Error getting documents.", task.getException());
                    }
                });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selectedCategory = categorySpinner.getSelectedItem().toString();
                String selectedSubCategory = subcategorySpinner.getSelectedItem().toString();
                String titleText = titleEditText.getText().toString();
                String descriptionText = descriptionEditText.getText().toString();
                int priceValue = Integer.parseInt(priceEditText.getText().toString());
                int discountValue = Integer.parseInt(discountEditText.getText().toString());
                String eventTypeText = eventTypesEditText.getText().toString();
                String selectedImagesText = selectedImagesEditText.getText().toString();


                String[] picturePaths = selectedImagesText.split(",");


                ArrayList<String> pictureList = new ArrayList<>(Arrays.asList(picturePaths));
                String availableText = availableCheckBox.isChecked() ? "Da" : "Ne";
                String visibleText = visibleCheckBox.isChecked() ? "Da" : "Ne";
                String status;
                if(selectedSubCategory!=null){
                    status = "PENDING";
                }else status = "APPROVAL";

                Product product = new Product(selectedCategory, selectedSubCategory, titleText, descriptionText, priceValue, discountValue, priceValue - priceValue * discountValue / 100, pictureList, eventTypeText, visibleText, availableText,status);

                // Spremanje podataka u Firestore
                db.collection("products").add(product)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("CreateProductActivity", "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("CreateProductActivity", "Error adding document", e);
                                  }
                        });
            }
        });
    }
}
