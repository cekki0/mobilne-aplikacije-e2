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
import java.util.HashMap;
import java.util.Map;

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
        pictureListEditText = findViewById(R.id.pictureListEditText);
        eventEditText = findViewById(R.id.eventEditText);
        availableEditText = findViewById(R.id.availableEditText);
        visibleEditText = findViewById(R.id.visibleEditText);
        saveButton = findViewById(R.id.saveButton);

        Product product = getIntent().getParcelableExtra("product");
        if (product != null) {
            titleEditText.setText(product.Title);
            descriptionEditText.setText(product.Description);
            priceEditText.setText(String.valueOf(product.Price));
            discountEditText.setText(String.valueOf(product.Discount));
            pictureListEditText.setText(convertPictureListToString(product.PictureList));
            eventEditText.setText(product.Event);
            availableEditText.setChecked(product.Available.equals("Yes"));
            visibleEditText.setChecked(product.Visible.equals("Yes"));
            subcategorySpinner.setText(String.valueOf(product.SubCategory));

            Log.d("DEBUG", "Available: " + product.Available);
            Log.d("DEBUG", "Visible: " + product.Visible);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product != null) {
                    // Ažuriraj cenu i popust
                    product.Price = Integer.parseInt(priceEditText.getText().toString());
                    product.Discount = Integer.parseInt(discountEditText.getText().toString());
                    double newPrice = product.Price * (1 - product.Discount / 100.0);

                    Map<String, Object> updatedData = new HashMap<>();
                    updatedData.put("Price", product.Price);
                    updatedData.put("Discount", product.Discount);
                    updatedData.put("NewPrice", newPrice);

                    db.collection("products")
                            .whereEqualTo("Title", product.Title)
                            .get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                                    db.collection("products").document(documentSnapshot.getId())
                                            .update(updatedData)
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

    private String convertPictureListToString(ArrayList<String> pictureList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String picture : pictureList) {
            stringBuilder.append(picture).append("\n");
        }
        return stringBuilder.toString();
    }
}