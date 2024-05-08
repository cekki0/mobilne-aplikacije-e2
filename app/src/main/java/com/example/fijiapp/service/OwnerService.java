package com.example.fijiapp.service;

import android.util.Log;

import com.example.fijiapp.model.Company;
import com.example.fijiapp.model.User;
import com.example.fijiapp.model.WorkingDay;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OwnerService {

    public Task<User> getUserByEmail(String userEmail) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection("users")
                .whereEqualTo("Email", userEmail)
                .get()
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                            return documentSnapshot.toObject(User.class);
                        }
                    }
                    return null;
                });
    }

    public Task<Company> getCompanyByOwnerEmail(String userEmail) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection("companies").whereEqualTo("OwnerEmail", userEmail).get()
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                            return documentSnapshot.toObject(Company.class);
                        }
                    }
                    throw new Exception("Failed to fetch company by userEmail");
                });
    }

    public Task<List<User>> getStaffByOwnerEmail(String userEmail) {
        return getCompanyByOwnerEmail(userEmail).continueWithTask(companyTask -> {
            Company company = companyTask.getResult();
            if (company != null) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                return db.collection("users").whereEqualTo("Company", company.Email).get()
                        .continueWith(task -> {
                            List<User> userList = new ArrayList<>();
                            if (task.isSuccessful()) {
                                QuerySnapshot querySnapshot = task.getResult();
                                if (querySnapshot != null & !querySnapshot.isEmpty()) {
                                    for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                                        User user = documentSnapshot.toObject(User.class);
                                        if (user != null) {
                                            userList.add(user);
                                        }
                                    }
                                }
                                return userList;
                            }
                            throw new Exception("Failed to fetch staff");
                        });
            }
            throw new Exception("Failed to fetch company");
        });
    }

    public void deactivateStaffAccount(String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection("users").document(email);
        ref.update("IsActive", false);
    }

    public void activateStaffAccount(String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection("users").document(email);
        ref.update("IsActive", true);
    }

    public void updateWorkHours(String email, List<WorkingDay> wh) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection("users").document(email);
        ref.update("WorkingDays", wh);
    }
}
