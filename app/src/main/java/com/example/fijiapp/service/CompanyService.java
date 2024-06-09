package com.example.fijiapp.service;

import android.util.Log;

import com.example.fijiapp.model.Company;
import com.example.fijiapp.repository.CategoryRepository;
import com.example.fijiapp.repository.CompanyRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class CompanyService {
    private CompanyRepository companyRepository;

    public CompanyService() {
        companyRepository = new CompanyRepository();
    }

    public Task<Company> getCompanyByOwnerEmail(String email) {

        return companyRepository.getCompanyByOwnerEmail(email)
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                            return documentSnapshot.toObject(Company.class);
                        }
                    }
                    return null;
                });
    }
}
