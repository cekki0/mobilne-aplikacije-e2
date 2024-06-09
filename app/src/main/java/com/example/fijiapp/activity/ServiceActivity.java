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
import com.example.fijiapp.adapters.ServiceAdapter;
import com.example.fijiapp.model.Service;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class ServiceActivity extends AppCompatActivity implements ServiceAdapter.OnItemClickListener {
    private List<Service> services = new ArrayList<>();
    private ServiceAdapter adapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        db = FirebaseFirestore.getInstance();

        RecyclerView recyclerView = findViewById(R.id.productRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ServiceAdapter(services, this, this);
        recyclerView.setAdapter(adapter);



        loadServicesFromFirestore();
    }

    private void loadServicesFromFirestore() {
        db.collection("services").whereEqualTo("status", "APPROVAL")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        services.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Service service = document.toObject(Service.class);
                            services.add(service);
                        }
                        adapter.notifyDataSetChanged();

                    } else {
                          }
                });
    }

    public void createServicePage(View view) {
        Intent intent = new Intent(this, CreateServiceActivity.class);
        startActivity(intent);
    }


    @Override
    public void onItemClick(Service service) {

    }
    @Override
    public void onDeleteButtonClick(Service service) {
        if (service != null) {
            String serviceName = service.getName();

            if (serviceName != null) {
                  db.collection("services")
                        .whereEqualTo("name", serviceName)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                document.getReference().update("status", "DELETED");
                            }

                             service.setStatus("DELETED");
                            adapter.notifyDataSetChanged();
                        })
                        .addOnFailureListener(e -> {
                             });
            }
        }
    }


}