package com.example.fijiapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import android.view.View;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fijiapp.R;
import com.example.fijiapp.model.Product;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateProductActivity extends AppCompatActivity {
    private EditText titleEditText, descriptionEditText, priceEditText, discountEditText, eventEditText, pictureListEditText, subcategorySpinner;
    private CheckBox availableEditText, visibleEditText;
    private Button saveButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        db = FirebaseFirestore.getInstance();

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        subcategorySpinner = findViewById(R.id.subcategorySpinner);
        priceEditText = findViewById(R.id.priceEditText);
        discountEditText = findViewById(R.id.discountEditText);
        priceEditText = findViewById(R.id.priceEditText);
        pictureListEditText = findViewById(R.id.pictureListEditText);
        eventEditText = findViewById(R.id.eventEditText);
        availableEditText = findViewById(R.id.availableEditText);
        visibleEditText = findViewById(R.id.visibleEditText);
        saveButton = findViewById(R.id.saveButton);

        Product product = getIntent().getParcelableExtra("product");
        if (product != null) {
            titleEditText.setText(product.Title);
            descriptionEditText.setText(product.Description);
            // Postaviti vrednost subcategorySpinner
            priceEditText.setText(String.valueOf(product.Price));
            discountEditText.setText(String.valueOf(product.Discount));
            //newPriceEditText.setText(String.valueOf(product.NewPrice));
            pictureListEditText.setText(convertPictureListToString(product.PictureList));
            eventEditText.setText(product.Event);
            availableEditText.setChecked(product.Available.equals("Yes"));
            visibleEditText.setChecked(product.Visible.equals("Yes"));
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product != null) {
                    product.Description = descriptionEditText.getText().toString();

                    // Čuvanje promena u bazi
                    db.collection("products")
                            .whereEqualTo("Title", product.Title)
                            .get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                                    db.collection("products").document(documentSnapshot.getId())
                                            .update("Description", product.Description)
                                            .addOnSuccessListener(aVoid -> {
                                                Toast.makeText(UpdateProductActivity.this, "Product updated successfully!", Toast.LENGTH_SHORT).show();
                                                finish();
                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(UpdateProductActivity.this, "Failed to update product.", Toast.LENGTH_SHORT).show();
                                                Log.e("UpdateProductActivity", "Error updating product", e);
                                            });
                                }
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(UpdateProductActivity.this, "Error querying database: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("UpdateProductActivity", "Error querying database", e);
                            });

                }
            }
        });
    }

    private ArrayList<String> convertStringToPictureList(String pictureListString) {
        String[] pictures = pictureListString.split("\n");
        return new ArrayList<>(Arrays.asList(pictures));
    }

    private String convertPictureListToString(ArrayList<String> pictureList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String picture : pictureList) {
            stringBuilder.append(picture).append("\n");
        }
        return stringBuilder.toString();
    }
}