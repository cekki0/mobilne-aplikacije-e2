package com.example.fijiapp.repository;

import com.example.fijiapp.model.User;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
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

    public Task<QuerySnapshot> getAllUsers() {
        return userRef.get();
    }
}
