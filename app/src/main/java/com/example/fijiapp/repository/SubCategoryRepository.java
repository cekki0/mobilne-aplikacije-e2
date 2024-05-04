package com.example.fijiapp.repository;

import com.example.fijiapp.model.Category;
import com.example.fijiapp.model.SubCategory;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class SubCategoryRepository {
    private CollectionReference subCategoryRef;

    public SubCategoryRepository() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        subCategoryRef = database.collection("subcategories");
    }

    public Task<DocumentReference> addSubCategory(SubCategory subCategory) {
        return subCategoryRef.add(subCategory);
    }

    public Task<Void> updateSubCategory(SubCategory subCategory) {
        String subCategoryId = subCategory.Id;
        if (subCategoryId == null) {
            return Tasks.forException(new IllegalArgumentException("SubCategory ID is null"));
        }

        return subCategoryRef.document(subCategoryId).set(subCategory);
    }

    public Task<Void> deleteSubCategory(String subCategoryName) {
        return subCategoryRef.document(subCategoryName).delete();
    }

    public Task<DocumentSnapshot> getSubCategoryById(String subCategoryId) {
        return subCategoryRef.document(subCategoryId).get();
    }

    public Task<QuerySnapshot> getAllSubCategories() {
        return subCategoryRef.get();
    }
}
