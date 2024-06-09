package com.example.fijiapp.repository;

import com.example.fijiapp.model.Company;
import com.example.fijiapp.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class CompanyRepository {

    private CollectionReference companyRef;

    public CompanyRepository() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        companyRef = database.collection("companies");
    }

    public Task<QuerySnapshot> getCompanyByOwnerEmail(String ownerEmail) {
        return companyRef.whereEqualTo("OwnerEmail", ownerEmail).get();
    }

}
