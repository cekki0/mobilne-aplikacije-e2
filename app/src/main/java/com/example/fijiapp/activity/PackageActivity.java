package com.example.fijiapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.adapters.PackageAdapter;
import com.example.fijiapp.model.Package;
import com.example.fijiapp.model.Product;
import com.example.fijiapp.model.Service;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PackageActivity extends AppCompatActivity {
    List<Package> packageList = new ArrayList<>();
    PackageAdapter adapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);

        db = FirebaseFirestore.getInstance();

        RecyclerView recyclerView = findViewById(R.id.packageRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PackageAdapter(packageList, this);
        recyclerView.setAdapter(adapter);

        SearchView searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        loadPackagesFromFirestore();

        adapter.setOnPackageDeleteClickListener(new PackageAdapter.OnPackageDeleteClickListener() {
            @Override
            public void onPackageDeleteClick(Package pack) {
                // Pozovi funkciju za brisanje paketa
                deletePackageByName(pack.getName());
            }
        });
    }

    private void loadPackagesFromFirestore() {
        db.collection("packages").whereEqualTo("status", "APPROVAL")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        packageList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Package packagee = document.toObject(Package.class);
                            packageList.add(packagee);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        // Handle errors
                    }
                });
    }

    private void filter(String query) {
        List<Package> filteredList = new ArrayList<>();
        if (TextUtils.isEmpty(query)) {
            filteredList.addAll(packageList);
        } else {
            String queryLowerCase = query.toLowerCase().trim();
            try {
                int priceFilter = Integer.parseInt(queryLowerCase);
                for (Package packagee : packageList) {
                    if (packagee.eventType != null && packagee.eventType.toLowerCase().contains(queryLowerCase) ||
                            packagee.getCategory() != null && packagee.getCategory().toLowerCase().contains(queryLowerCase) ||
                            (packagee.getPrice() <= priceFilter)){
                        filteredList.add(packagee);
                    }
                }
            } catch (NumberFormatException e) {
                for (Package packagee : packageList) {
                    if (packagee.eventType != null && packagee.eventType.toLowerCase().contains(queryLowerCase) ||
                            packagee.getCategory() != null && packagee.getCategory().toLowerCase().contains(queryLowerCase)
                            ){
                        filteredList.add(packagee);
                    }
                }
            }
        }
        adapter.filterList(filteredList);
    }


    public void createPackagePage(View view) {
        Intent intent = new Intent(this, CreatePackageActivity.class);
        startActivity(intent);
    }

    private void deletePackageByName(String packageName) {
        db.collection("packages").whereEqualTo("name", packageName)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            documentSnapshot.getReference().delete();
                        }

                        for (Package pack : packageList) {
                            if (pack.getName().equals(packageName)) {
                                packageList.remove(pack);
                                adapter.notifyDataSetChanged();
                                break;
                            }
                        }
                    }
                })
                .addOnFailureListener(e -> {

                });
    }
}