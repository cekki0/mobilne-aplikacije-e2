package com.example.fijiapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.adapters.PackageAdapter;
import com.example.fijiapp.adapters.ProductAdapter;
import com.example.fijiapp.adapters.ProductCheckBoxAdapter;
import com.example.fijiapp.adapters.ServiceAdapter;
import com.example.fijiapp.adapters.ServiceCheckBoxAdapter;
import com.example.fijiapp.model.Package;
import com.example.fijiapp.model.Product;
import com.example.fijiapp.model.Service;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdatePackageActivity extends AppCompatActivity {
    private FirebaseFirestore db;

    private EditText editTextName, editTextDescription, editTextDiscount, editTextEventType, editTextPrice,
            editTextBookingDeadline, editTextCancellationDeadline, imagesEditText, servicesEditText;
    private CheckBox checkBoxVisible, checkBoxAvailable;
    private RecyclerView recyclerViewProducts, recyclerViewServices, recyclerView, recyclerViewForProducts;
    private Button buttonAddProduct, buttonAddService, buttonSaveChanges;

    private ProductAdapter productAdapter;
    private ProductCheckBoxAdapter productCheckBoxAdapter;
    private ServiceAdapter serviceAdapter;;
    private ServiceCheckBoxAdapter serviceCheckBoxAdapter;

    private List<Product> productList = new ArrayList<>();
    private List<Service> serviceList = new ArrayList<>();

    private Package packageToUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_package);

        db = FirebaseFirestore.getInstance();

        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewServices = findViewById(R.id.recyclerViewServices);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewServices.setLayoutManager(new LinearLayoutManager(this));

        editTextName = findViewById(R.id.editTextName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDiscount = findViewById(R.id.editTextDiscount);
        editTextEventType = findViewById(R.id.editTextEventType);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextBookingDeadline = findViewById(R.id.editTextBookingDeadline);
        editTextCancellationDeadline = findViewById(R.id.editTextCancellationDeadline);
        imagesEditText = findViewById(R.id.imagesEditText);

        checkBoxVisible = findViewById(R.id.checkBoxVisible);
        checkBoxAvailable = findViewById(R.id.checkBoxAvailable);

        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewServices = findViewById(R.id.recyclerViewServices);

        buttonAddProduct = findViewById(R.id.buttonAddProduct);
        buttonAddService = findViewById(R.id.buttonAddService);
        buttonSaveChanges = findViewById(R.id.buttonSaveChanges);

        productAdapter = new ProductAdapter(productList, this);
        recyclerViewProducts.setAdapter(productAdapter);

        packageToUpdate = getIntent().getParcelableExtra("package");
        if (packageToUpdate != null) {
            loadPackageData(packageToUpdate);
        }

        buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePackageDescription();
            }
        });
    }

    private void loadPackageData(Package packageToUpdate) {
        editTextName.setText(packageToUpdate.getName());
        editTextDescription.setText(packageToUpdate.getDescription());
        editTextDiscount.setText(String.valueOf(packageToUpdate.getDiscount()));
        editTextEventType.setText(packageToUpdate.eventType);
        editTextPrice.setText(String.valueOf(packageToUpdate.getPrice()));
        editTextBookingDeadline.setText(packageToUpdate.bookingDeadline);
        editTextCancellationDeadline.setText(packageToUpdate.cancellationDeadline);

        if (packageToUpdate.getImages() != null && !packageToUpdate.getImages().isEmpty()) {
            StringBuilder imagesText = new StringBuilder();
            for (String image : packageToUpdate.getImages()) {
                imagesText.append(image).append("\n");
            }
            imagesEditText.setText(imagesText.toString());
        }

        checkBoxVisible.setChecked(packageToUpdate.getVisible().equals("Da"));
        checkBoxAvailable.setChecked(packageToUpdate.getAvailable().equals("Da"));

        // Load products and services here if needed
    }

    private void updatePackageDescription() {
        String name = editTextName.getText().toString();
        String newDescription = editTextDescription.getText().toString();

        // Kreiramo mapu sa samo poljem za ažuriranje deskripcije
        Map<String, Object> updatedFields = new HashMap<>();
        updatedFields.put("description", newDescription);

        // Tražimo pakete po imenu
        db.collection("packages")
                .whereEqualTo("name", name)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String packageId = documentSnapshot.getId();

                            // Ažuriramo deskripciju paketa
                            db.collection("packages").document(packageId)
                                    .update(updatedFields)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("UpdatePackageActivity", "Package description updated successfully");
                                            // Handle successful update
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("UpdatePackageActivity", "Error updating package description", e);
                                            // Handle update failure
                                        }
                                    });
                            break; // Prekidamo petlju jer smo pronašli paket
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("UpdatePackageActivity", "Error finding package by name", e);
                        // Handle error finding package
                    }
                });
    }

}





