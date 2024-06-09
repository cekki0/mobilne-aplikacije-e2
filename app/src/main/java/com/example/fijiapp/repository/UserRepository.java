package com.example.fijiapp.repository;

import android.util.Log;

import com.example.fijiapp.model.SubCategory;
import com.example.fijiapp.model.User;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class UserRepository {
    private CollectionReference userRef;

    public UserRepository() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        userRef = database.collection("users");
    }

    public Task<Void> updateUser(User user) {
        String userId = user.Id;
        if (userId == null) {
            return Tasks.forException(new IllegalArgumentException("User ID is null"));
        }

        return userRef.document(userId).set(user);
    }

    public Task<Void> deleteUser(User user) {
        String userId = user.Id;
        return userRef.document(userId).delete();
    }

    public Task<DocumentSnapshot> getUserById(String userId) {
        return userRef.document(userId).get();
    }

    public Task<QuerySnapshot> getAllUsers() {
        return userRef.get();
    }
}
