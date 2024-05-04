package com.example.fijiapp.repository;

import com.example.fijiapp.model.Category;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.logging.Logger;

public class CategoryRepository {
    private CollectionReference categoryRef;

    public CategoryRepository() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        categoryRef = database.collection("categories");
    }

    public Task<Void> addCategory(Category category) {
        return categoryRef.document(category.Name).set(category);
    }

    public Task<Void> updateCategory(Category category) {
        return categoryRef.document(category.Name).set(category);
    }

    public Task<Void> deleteCategory(String categoryName) {
        return categoryRef.document(categoryName).delete();
    }

    public Task<QuerySnapshot> getAllCategories() {
        return categoryRef.get();
    }
}
