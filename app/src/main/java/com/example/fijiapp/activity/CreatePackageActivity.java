package com.example.fijiapp.activity;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.adapters.ProductAdapter;
import com.example.fijiapp.adapters.ProductCheckBoxAdapter;
import com.example.fijiapp.adapters.ServiceCheckBoxAdapter;
import com.example.fijiapp.model.Product;
import com.example.fijiapp.model.Service;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreatePackageActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private EditText nameEditText;
    private EditText descriptionEditText;
    private EditText discountEditText;
    private CheckBox visibleEditText;
    private CheckBox availableEditText;
    private Spinner categorySpinner;
    private List<Product> products;
    private List<Service> services;
    private EditText eventTypeEditText;
    private EditText priceEditText;
    private EditText imagesEditText;
    private EditText bookingDeadlineEditText;
    private EditText cancellationDeadlineEditText;
    private Button createPackageButton;
    private RecyclerView recyclerViewProducts, recyclerViewServices, recyclerView,recyclerViewForProducts;
    private ServiceCheckBoxAdapter serviceCheckBoxAdapter;
    private ProductCheckBoxAdapter productCheckBoxAdapter;
    private List<Service> servicesList = new ArrayList<>();
    private List<Product> productsList = new ArrayList<>();
    private List<String> categoryList = new ArrayList<>();

    Map<String, List<String>> categorySubcategoryMap = new HashMap<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_package);

        db = FirebaseFirestore.getInstance();

        imagesEditText = findViewById(R.id.imagesEditText);

        createPackageButton = findViewById(R.id.createPackageButton);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewForProducts = findViewById(R.id.recyclerViewForProducts);

        productCheckBoxAdapter = new ProductCheckBoxAdapter(this, productsList);
        recyclerViewForProducts.setAdapter(productCheckBoxAdapter);

        serviceCheckBoxAdapter = new ServiceCheckBoxAdapter(this, servicesList);
        recyclerView.setAdapter(serviceCheckBoxAdapter);



        nameEditText = findViewById(R.id.nameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        discountEditText = findViewById(R.id.discountEditText);
        visibleEditText = findViewById(R.id.visibleEditText);
        availableEditText = findViewById(R.id.availableEditText);
        categorySpinner = findViewById(R.id.categorySpinner);
        eventTypeEditText = findViewById(R.id.eventTypeEditText);
        priceEditText = findViewById(R.id.priceEditText);
        imagesEditText = findViewById(R.id.imagesEditText);
        bookingDeadlineEditText = findViewById(R.id.bookingDeadlineEditText);
        cancellationDeadlineEditText = findViewById(R.id.cancellationDeadlineEditText);
        createPackageButton = findViewById(R.id.createPackageButton);

        List<String> fotoSubcategories = Arrays.asList("Fotografije i Albumi", "Radosnice", "Maturanti","Custom");
        List<String> laptopSubcategories = Arrays.asList("Dell", "HP", "Lenovo","Custom");

        categorySubcategoryMap.put("Foto i Video", fotoSubcategories);
        categorySubcategoryMap.put("Laptops", laptopSubcategories);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
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
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });




        recyclerViewForProducts = findViewById(R.id.recyclerViewForProducts);
        recyclerViewForProducts.setLayoutManager(new LinearLayoutManager(this));





        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dohvatiListuProizvoda();
        dohvatiListuServisa();

        createPackageButton.setOnClickListener(v -> {
            String packageName = nameEditText.getText().toString().trim();
            String packageDescription = descriptionEditText.getText().toString().trim();
            int packageDiscount = Integer.parseInt(discountEditText.getText().toString().trim());
            String packageVisible = visibleEditText.isChecked() ? "Yes" : "No";
            String packageAvailable = availableEditText.isChecked() ? "Yes" : "No";
            String packageCategory = categorySpinner.getSelectedItem().toString();
            String packageEventType = eventTypeEditText.getText().toString().trim();
            int packagePrice = Integer.parseInt(priceEditText.getText().toString().trim());
            List<String> packageImages = Arrays.asList(imagesEditText.getText().toString().trim().split(","));
            String packageBookingDeadline = bookingDeadlineEditText.getText().toString().trim();
            String packageCancellationDeadline = cancellationDeadlineEditText.getText().toString().trim();


            List<Product> selectedProducts = productCheckBoxAdapter.getSelectedProducts();


            List<Service> selectedServices = serviceCheckBoxAdapter.getSelectedServices();

            if (packageName.isEmpty() || packageDescription.isEmpty()) {
                Toast.makeText(CreatePackageActivity.this, "Molimo vas da popunite sva polja", Toast.LENGTH_SHORT).show();
                return;
            }


            Map<String, Object> packageData = new HashMap<>();
            packageData.put("name", packageName);
            packageData.put("description", packageDescription);
            packageData.put("discount", packageDiscount);
            packageData.put("visible", packageVisible);
            packageData.put("available", packageAvailable);
            packageData.put("category", packageCategory);
            packageData.put("event_type", packageEventType);
            packageData.put("price", packagePrice);
            packageData.put("images", packageImages);
            packageData.put("booking_deadline", packageBookingDeadline);
            packageData.put("cancellation_deadline", packageCancellationDeadline);
            packageData.put("products", selectedProducts);
            packageData.put("services", selectedServices);
            packageData.put("status","APPROVAL");

            db.collection("packages")
                    .add(packageData)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(CreatePackageActivity.this, "Paket uspješno spremljen", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(CreatePackageActivity.this, "Greška prilikom spremanja paketa: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Greška prilikom spremanja paketa", e);
                    });
        });







    }


    private void dohvatiListuServisa() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("services")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Service> servicesList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Service service = document.toObject(Service.class);
                            servicesList.add(service);
                        }
                        // Postavljanje liste servisa u adapter
                        postaviServiseUAdapter(servicesList);
                    } else {
                        Log.d(TAG, "Greška prilikom dohvaćanja servisa: ", task.getException());
                    }
                });
    }

    // Postavljanje liste servisa u adapter
    private void postaviServiseUAdapter(List<Service> servicesList) {
        serviceCheckBoxAdapter = new ServiceCheckBoxAdapter(this, servicesList);
        recyclerView.setAdapter(serviceCheckBoxAdapter);
    }
    private void dohvatiListuProizvoda() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("products")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Product> productsList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Product product = document.toObject(Product.class);
                            productsList.add(product);

                        }
                        // Postavljanje liste proizvoda u adapter
                        postaviProizvodeUAdapter(productsList);
                    } else {
                        Log.d(TAG, "Greška prilikom dohvaćanja proizvoda: ", task.getException());
                    }
                });
    }

    // Postavljanje liste proizvoda u adapter
    private void postaviProizvodeUAdapter(List<Product> productsList) {
        RecyclerView recyclerViewForProducts = findViewById(R.id.recyclerViewForProducts);
        ProductCheckBoxAdapter adapter = new ProductCheckBoxAdapter(this, productsList);
        recyclerViewForProducts.setAdapter(adapter);
    }

}
