package com.example.fijiapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.adapters.ProductAdapter;
import com.example.fijiapp.model.Product;
import com.example.fijiapp.model.Service;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import android.view.View;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProductsActivity extends AppCompatActivity implements ProductAdapter.OnItemClickListener {
    private List<Product> products = new ArrayList<>();
    private ProductAdapter adapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        db = FirebaseFirestore.getInstance();

        RecyclerView recyclerView = findViewById(R.id.productRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProductAdapter(products, this);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

       

        loadProductsFromFirestore();
    }
    private void loadProductsFromFirestore() {
        db.collection("products").whereEqualTo("Status", "APPROVAL")

                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        products.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Product product = document.toObject(Product.class);
                            products.add(product);
                        }
                        adapter.notifyDataSetChanged();
                    } else {

                    }
                });
    }



    @Override
    public void onItemClickProduct(Product product) {

    }

    @Override
    public void onDeleteButtonClickProduct(Product product) {
        if (product != null) {
            String productName = product.Title;
            if (productName != null) {

                db.collection("products")
                        .whereEqualTo("Title", productName)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                document.getReference().update("Status", "DELETED");
                            }
                            // Ažuriranje uspešno završeno, obavesti adapter da su podaci promenjeni
                            product.Status ="DELETED";
                            adapter.notifyDataSetChanged();
                        })
                        .addOnFailureListener(e -> {
                             });
            }
        }
    }

    public void createProductPage(View view) {
        Intent intent = new Intent(this, CreateProductActivity.class);
        startActivity(intent);
    }
}