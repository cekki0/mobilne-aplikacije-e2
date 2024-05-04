package com.example.fijiapp.repository;

import com.example.fijiapp.model.Category;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class CategoryRepository {
    private CollectionReference categoryRef;

    public CategoryRepository() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        categoryRef = database.collection("categories");
    }

    public Task<DocumentReference> addCategory(Category category) {
        return categoryRef.add(category);
    }

    public Task<Void> updateCategory(Category category) {
        String categoryId = category.Id;
        if (categoryId == null) {
            return Tasks.forException(new IllegalArgumentException("Category ID is null"));
        }

        return categoryRef.document(categoryId).set(category);
    }

    public Task<Void> deleteCategory(String categoryId) {
        return categoryRef.document(categoryId).delete();
    }

    public Task<QuerySnapshot> getAllCategories() {
        return categoryRef.get();
    }
}
