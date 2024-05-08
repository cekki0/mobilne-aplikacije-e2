package com.example.fijiapp.repository;

import com.example.fijiapp.model.Service;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ServiceRepository {

    private final FirebaseFirestore database;
    private final CollectionReference serviceRef;

    public ServiceRepository() {
        database = FirebaseFirestore.getInstance();
        serviceRef = database.collection("services");
    }

    public Task<Service> getServiceById(String serviceId) {
        return serviceRef.document(serviceId).get().continueWith(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    return document.toObject(Service.class);
                }
            }
            return null;
        });
    }
}
